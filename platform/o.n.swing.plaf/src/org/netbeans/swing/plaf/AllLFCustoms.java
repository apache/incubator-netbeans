/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.swing.plaf;

import org.netbeans.swing.plaf.util.GuaranteedValue;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.plaf.metal.MetalLookAndFeel;

/** Customization for all LFs. */
final class AllLFCustoms extends LFCustoms {

    // Monitor diagonals for determining a font size based
    // on monitor size
    private static final int TRADITIONAL_MONITOR_480 = 640 * 480;
    private static final int TRADITIONAL_MONITOR_600 = 800 * 600;
    private static final int TRADITIONAL_MONITOR_768 = 1024 * 768;
    private static final int TRADITIONAL_MONITOR_1024 = 1280 * 1024;
    private static final int TRADITIONAL_MONITOR_1600 = 1600 * 1200;
    private static final int HI_DEF_MONITOR_720 = 1280 * 720;
    private static final int HI_DEF_MONITOR_900 = 1600 * 900;
    private static final int HI_DEF_MONITOR_FHD = 1920 * 1080;
    private static final int HI_DEF_MONITOR_QHD = 2560 * 1440;
    private static final int HI_DEF_MONITOR_4K = 3840 * 2160;
    private static final int HI_DEF_MONITOR_8K = 7680 * 4320;
    private static final int DEFAULT_CHARS_PER_INCH = 9;

    public Object[] createApplicationSpecificKeysAndValues() {
        Object[] uiDefaults = {

            //Tab control in case of unknown look and feel
            TAB_ACTIVE_SELECTION_BACKGROUND,
            new GuaranteedValue(new String[]{"Table.selectionBackground",
                "info"}, Color.BLUE.brighter()),

            TAB_ACTIVE_SELECTION_FOREGROUND,
            new GuaranteedValue("Table.selectionForeground",
            Color.WHITE),

            TAB_SELECTION_FOREGROUND,
            new GuaranteedValue("textText", Color.BLACK),

            //Likely to be the same for all look and feels - doesn't do anything
            //exciting
            EDITOR_TABBED_CONTAINER_UI,
            "org.netbeans.swing.tabcontrol.plaf.DefaultTabbedContainerUI",

            SLIDING_TAB_DISPLAYER_UI,
            "org.netbeans.swing.tabcontrol.plaf.BasicSlidingTabDisplayerUI",

            SLIDING_TAB_BUTTON_UI,
            "org.netbeans.swing.tabcontrol.plaf.SlidingTabDisplayerButtonUI",

            SLIDING_BUTTON_UI, "org.netbeans.swing.tabcontrol.SlidingButtonUI", //NOI18N


            SCROLLPANE_BORDER_COLOR, new Color(127, 157, 185),

            EDITOR_ERRORSTRIPE_SCROLLBAR_INSETS, new Insets(0, 0, 0, 0),

            SPLIT_PANE_DIVIDER_SIZE_VERTICAL, new Integer(4),
            SPLIT_PANE_DIVIDER_SIZE_HORIZONTAL, new Integer(4)
        }; //NOI18N
        return uiDefaults;
    }

    public Object[] createGuaranteedKeysAndValues() {
        //ColorUIResource errorColor = new ColorUIResource(89, 79, 191);
        // 65358: asked Red color for error messages
        ColorUIResource errorColor = new ColorUIResource(255, 0, 0);
        //#204598 - there's no cross-platform warning-like color...
        ColorUIResource warningColor = new ColorUIResource(51, 51, 51);

        int fontsize = 13;
        Integer in = (Integer) UIManager.get(CUSTOM_FONT_SIZE); //NOI18N
        boolean hasCustomFontSize = in != null;
        if (hasCustomFontSize) {
            fontsize = in.intValue();
        } else {
            fontsize = adjustFontSizeForScreenSize(fontsize);
            UIManager.put(CUSTOM_FONT_SIZE, fontsize);
        }
        Object[] uiDefaults = {
            //XXX once jdk 1.5 b2 is out, these can be deleted

            "control", new GuaranteedValue("control", Color.LIGHT_GRAY),
            "controlShadow", new GuaranteedValue("controlShadow", Color.GRAY),
            "controlDkShadow", new GuaranteedValue("controlDkShadow", Color.DARK_GRAY),
            "textText", new GuaranteedValue("textText", Color.BLACK),
            "controlFont", new GuaranteedValue("controlFont",
            new Font("Dialog", Font.PLAIN, fontsize)),

            DEFAULT_FONT_SIZE, fontsize,
            ERROR_FOREGROUND, new GuaranteedValue(ERROR_FOREGROUND, errorColor),

            WARNING_FOREGROUND, new GuaranteedValue(WARNING_FOREGROUND, warningColor ),

        };
        return uiDefaults;
    }
    private static boolean sameResolution(DisplayMode a, DisplayMode b) {
        return a.getWidth() == b.getWidth() && a.getHeight() == b.getHeight();
    }

    private static DisplayMode findLargestDisplayMode(DisplayMode[] mode) {
        Arrays.sort(mode, new DisplayModeComparator());
        return mode[0];
    }

    private static final class DisplayModeComparator implements Comparator<DisplayMode> {

        @Override
        public int compare(DisplayMode a, DisplayMode b) {
            return Integer.compare(b.getWidth() * b.getHeight(), a.getWidth() * a.getHeight());
        }
    }

    private static GraphicsDevice getLargestGraphicsDevice() {
        java.util.List<GraphicsDevice> devices = new ArrayList<>();
        for (GraphicsDevice device : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            if (device.getType() == GraphicsDevice.TYPE_RASTER_SCREEN) {
                devices.add(device);
            }
        }
        Collections.sort(devices, new GraphicsDeviceComparator());
        return devices.isEmpty() ? null : devices.get(0);
    }

    private static AffineTransform createScalingTransform(DisplayMode a, DisplayMode b) {
        double xScale = (double) a.getWidth() / (double) b.getWidth();
        double yScale = (double) a.getHeight() / (double) b.getHeight();
        return AffineTransform.getScaleInstance(xScale, yScale);
    }

    private static boolean osRequiresAdjustingFontSize() {
        String os = System.getProperty("os.name"); //NOI18N
        return os == null || !os.contains("win") && !os.contains("mac"); //NOI18N
    }

    /**
     * Gets the default GraphicsDevice; uses its configuration's transform
     * to 72DPI to find a font size that fits as closely as possible within
     * 10 characters per inch (customizable with the system property
     * screenTargetCpi).
     *
     * @param initialTarget The default font size if none is set
     * @return A font size tuned to the monitor
     */
    public static int adjustFontSizeForScreenSize(int initialTarget) {
        if (GraphicsEnvironment.isHeadless() // possible in tests
                // assume windows and mac are taken care of elsewhere
                || !osRequiresAdjustingFontSize()) {
            return initialTarget;
        }
        GraphicsDevice largest = getLargestGraphicsDevice();

        // On Linux, anyway, the "normalizing" transform the graphics device
        // will return will be the same at *all* resolutions.  So we should not
        // assume that is useful.

        // Instead, we get the largest possible graphics configuration - which
        // is most likely to be the native monitor resolution, and scale based
        // on that.
        DisplayMode dm = largest.getDisplayMode();
        DisplayMode largestDisplayMode = findLargestDisplayMode(largest.getDisplayModes());

        // It may be that the "default configuration" is not the one in use,
        // which is why we always get the native screen resolution, but it
        // is the one we get

        GraphicsConfiguration config = largest.getDefaultConfiguration();

        int diagonal = dm.getWidth() * dm.getHeight();

        // Get the screen dpi
        int pixelsPerInch = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();

        // Get the default transform for the device.
        AffineTransform defaultTransform = config.getDefaultTransform();

        AffineTransform scalingTransform;
        if (!sameResolution(largestDisplayMode, dm)) {
            scalingTransform = createScalingTransform(largestDisplayMode, dm);
        } else {
            scalingTransform = new AffineTransform();
        }

        // Start with a base font size based on the screen resolution.
        int baseFontSize = baseFontSizeByDiagonal(diagonal, initialTarget);

        // Then attempt to apply it in actual device space and scale it up or
        // down until we have a font size that meets our target characters
        // per physical screen insh
        VolatileImage img = config.createCompatibleVolatileImage(1, 1);
        Graphics2D g = img.createGraphics();
        g.setTransform(defaultTransform);
        try {
            return findBestFontSize(g, scalingTransform, baseFontSize, pixelsPerInch);
        } finally {
            g.dispose();
        }
    }

    private static int findBestFontSize(Graphics2D g, AffineTransform scalingTransform, int baseFontSize, int pixelsPerInch) {
        int result = baseFontSize;
        // Get a ten character of X's which is a wide character in most fonts
        String test = cpiMeasurementString();
        int adjustmentDirection = 1;
        boolean first = true;
        // In practice, the default values are within 1-2 point sizes of the
        // ideal value, so this does not loop heavily unless an exception was
        // thrown earlier
        int lastStringWidth = Integer.MAX_VALUE;
        for (int i = 0; i < 48; i++) {
            if (result + (i * adjustmentDirection) < 0) {
                break;
            }
            // Create a scaled font.  Use the JDK's sans serif since it is
            // always present, and most fonts are similar - at the time we
            // are called, the L&F is incompletely initialized, so trying to
            // get the value is likely to get one that will be replaced (and
            // it probably is Sans Serif anyway).
            Font f = new Font("Sans Serif", Font.PLAIN, result + (i * adjustmentDirection));
            f = f.deriveFont(scalingTransform);
            // Transform it into our inverted 72dpi space
            FontMetrics fm = g.getFontMetrics(f);
            // Get the width of this string - 72px = one inch, so we search
            // for the nearest value between a string width < dpi and a string
            // width > dpi
            int w = fm.stringWidth(test);
            if (first) {
                // First loop - unless it's exact, just figure out which
                // direction we need to loop in
                if (w > pixelsPerInch) {
                    adjustmentDirection = -1;
                } else if (w < pixelsPerInch) {
                    adjustmentDirection = 1;
                } else {
                    result += (i * adjustmentDirection);
                    break;
                }
                first = false;
            } else {
                // If we are at a boundary, set the value to return and break
                if (w < pixelsPerInch && adjustmentDirection == -1) {
                    int newResult = result + (i * adjustmentDirection);
                    if (Math.abs(pixelsPerInch - w) < Math.abs(pixelsPerInch - lastStringWidth)) {
                        result = newResult;
                    }
                    break;
                } else if (w > pixelsPerInch && adjustmentDirection == 1) {
                    int newResult = result + (i * adjustmentDirection);
                    if (Math.abs(pixelsPerInch - w) < Math.abs(pixelsPerInch - lastStringWidth)) {
                        result = newResult;
                    }
                    break;
                }
            }
            lastStringWidth = w;
        }
        return result;
    }

    /**
     * Inverse-compares GraphicsDevices by screen diagonal.
     */
    private static final class GraphicsDeviceComparator implements Comparator<GraphicsDevice> {

        @Override
        public int compare(GraphicsDevice a, GraphicsDevice b) {
            DisplayMode dm = a.getDisplayMode();
            int awh = dm.getWidth() * dm.getHeight();
            int bwh = dm.getWidth() * dm.getHeight();
            return -Integer.compare(awh, bwh);
        }
    }

    private static String cpiMeasurementString() {
        char[] chars = new char[targetCharsPerInch()];
        Arrays.fill(chars, 'X'); // NOI18N
        return new String(chars);
    }

    /**
     * Get the default target characters per inch, or the value returned
     * by the system property.
     *
     * @return An integer for the target characters per inch
     */
    private static int targetCharsPerInch() {
        String value = System.getProperty("screenTargetCpi"); // NOI18N
        if (value != null) {
            try {
                int result = Integer.parseInt(value);
                if (result < 0) {
                    throw new NumberFormatException("screenTargetCpi must be > 0"); // NOI18N
                }
                return result;
            } catch (NumberFormatException nfe) {
                Logger.getLogger(AllLFCustoms.class.getName()).log(Level.WARNING,
                        "Bad value for system property screenTargetCpi: ''{0}''", // NOI18N
                        value);
            }
        }
        return DEFAULT_CHARS_PER_INCH;
    }

    /**
     * Find base values based on the diagonal pixel count of the screen.
     *
     * @param diagonal The reported width * height of the screen
     * @param defaultBaseSize The default base font size
     * @return
     */
    static int baseFontSizeByDiagonal(int diagonal, int defaultBaseSize) {
        switch (diagonal) {
            case HI_DEF_MONITOR_8K:
                return 36;
            case HI_DEF_MONITOR_4K:
                return 28;
            case HI_DEF_MONITOR_QHD :
                return 20;
            case HI_DEF_MONITOR_FHD :
                return 18;
            case HI_DEF_MONITOR_900 :
                return 14;
            case HI_DEF_MONITOR_720 :
                return 13;
            case TRADITIONAL_MONITOR_1600 :
                return 15;
            case TRADITIONAL_MONITOR_1024:
                return 13;
            case TRADITIONAL_MONITOR_768 :
                return 12;
            case TRADITIONAL_MONITOR_600:
                return 11;
            case TRADITIONAL_MONITOR_480:
                return 10;
            default:
                // If we did not get handed an exact match, find the
                // nearest.  Simply setting your window manager to have
                // screen margins will result in an inexact match.
                int[] allResolutions = new int[]{
                    TRADITIONAL_MONITOR_1600, TRADITIONAL_MONITOR_1024,
                    TRADITIONAL_MONITOR_768,
                    TRADITIONAL_MONITOR_600, TRADITIONAL_MONITOR_480,
                    HI_DEF_MONITOR_8K, HI_DEF_MONITOR_4K, HI_DEF_MONITOR_QHD,
                    HI_DEF_MONITOR_FHD, HI_DEF_MONITOR_900, HI_DEF_MONITOR_720
                };
                Arrays.sort(allResolutions);
                int direction = 0;
                // Find the nearest and recursively call this method, this time
                // with a constant that will be caught by ths switch above.
                for (int i = 0; i < allResolutions.length; i++) {
                    int currentResolution = allResolutions[i];
                    int currentDirection = Integer.compare(diagonal, currentResolution);
                    if (direction == 0) {
                        direction = currentDirection;
                    } else if (currentDirection != direction) {
                        return baseFontSizeByDiagonal(allResolutions[i],
                                defaultBaseSize);
                    }
                }
        }
        return defaultBaseSize;
    }

    public static void initCustomFontSize(int uiFontSize) {
        Font nbDialogPlain = new FontUIResource("Dialog", Font.PLAIN, uiFontSize); // NOI18N
        Font nbDialogBold = new FontUIResource("Dialog", Font.BOLD, uiFontSize); // NOI18N
        Font nbSerifPlain = new FontUIResource("Serif", Font.PLAIN, uiFontSize); // NOI18N
        Font nbSansSerifPlain = new FontUIResource("SansSerif", Font.PLAIN, uiFontSize); // NOI18N
        Font nbMonospacedPlain = new FontUIResource("Monospaced", Font.PLAIN, uiFontSize); // NOI18N

        Map<Font, Font> fontTranslation = new HashMap<Font, Font>(5);

        if ("Nimbus".equals(UIManager.getLookAndFeel().getID())) { //NOI18N
            switchFont("defaultFont", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        }
        switchFont("controlFont", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("Button.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("ToggleButton.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("RadioButton.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("CheckBox.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("ColorChooser.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("ComboBox.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("Label.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("List.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("FileChooser.listFont", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("MenuBar.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("MenuItem.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("MenuItem.acceleratorFont", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("RadioButtonMenuItem.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("CheckBoxMenuItem.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("Menu.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("PopupMenu.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("OptionPane.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("OptionPane.messageFont", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("Panel.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("ProgressBar.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("ScrollPane.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("Viewport.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("TabbedPane.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("Table.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("TableHeader.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("TextField.font", fontTranslation, uiFontSize, nbSansSerifPlain); // NOI18N
        switchFont("PasswordField.font", fontTranslation, uiFontSize, nbMonospacedPlain); // NOI18N
        switchFont("TextArea.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("TextPane.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("EditorPane.font", fontTranslation, uiFontSize, nbSerifPlain); // NOI18N
        switchFont("TitledBorder.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("ToolBar.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("ToolTip.font", fontTranslation, uiFontSize, nbSansSerifPlain); // NOI18N
        switchFont("Tree.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("InternalFrame.titleFont", fontTranslation, uiFontSize, nbDialogBold); // NOI18N
        switchFont("windowTitleFont", fontTranslation, uiFontSize, nbDialogBold); // NOI18N
        switchFont("Spinner.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
        switchFont("FormattedTextField.font", fontTranslation, uiFontSize, nbDialogPlain); // NOI18N
    }

    //#144402
    private static final boolean isMetal = null != UIManager.getLookAndFeel()
            && UIManager.getLookAndFeel().getClass() == MetalLookAndFeel.class;
    private static final boolean isWindows = null != UIManager.getLookAndFeel()
            && "Windows".equals(UIManager.getLookAndFeel().getID());

    private static void switchFont(String uiKey, Map<Font, Font> fontTranslation, int uiFontSize, Font defaultFont) {
        Font oldFont = UIManager.getFont(uiKey);
        Font newFont = (null == oldFont || isMetal) ? defaultFont : fontTranslation.get(oldFont);
        if (null == newFont) {
            if (isWindows) {
                newFont = oldFont.deriveFont((float) uiFontSize);
            } else {
                newFont = new FontUIResource(oldFont.getFontName(), oldFont.getStyle(), uiFontSize);
            }
            fontTranslation.put(oldFont, newFont);
        }
        UIManager.put(uiKey, newFont);
    }
}
