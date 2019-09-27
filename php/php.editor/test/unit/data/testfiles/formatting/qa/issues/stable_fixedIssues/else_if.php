<?php
if (isset($this->record['message'])) {
    $message = $this->record['message'];
}
else {
    $this->error("Invalid log record. No message.");
    return;
}


if (!strcmp($message, 'USG_ENABLED_MODULES')) {
    $module_list = $this->record['params'];
    sort($module_list);
    if (isset($this->enabled_modules)) {
        if ($this->enabled_modules == $module_list) {

            return;
        }
    }

    $this->enabled_modules = $module_list;
    }
    elseif (!strcmp($message, 'USG_DISABLED_MODULES')) {
        $module_list = $this->record['params'];
        sort($module_list);
        if (isset($this->disabled_modules)) {
            if ($this->disabled_modules == $module_list) {

                return;
            }
        }

        $this->disabled_modules = $module_list;
        }
        elseif (!strcmp($message, 'USG_INSTALLED_CLUSTERS')) {
            $cluster_list = $this->record['params'];
            sort($cluster_list);
            if (isset($this->installed_clusters)) {
                if ($this->installed_clusters == $cluster_list) {

                    return;
                }
            }

            $this->installed_clusters = $cluster_list;
        }
?>