<?php

    class BlockedItemsDataSink extends ItemDataSink {

        protected function onConstruct() {
            parent::onConstruct();

            $this->setParam(self::TABLE, "_blocked_items");
            $this->setParam(self::FIELDS, array_merge($this->getParam(self::FIELDS), array(
                'items_item_id' => array('_type' => G_CreoleDataFlow::TYPE_INTEGER),
                'items_item_type' => array('_type' => G_CreoleDataFlow::TYPE_STRING),
                'items_item_status' => array('_type' => G_CreoleDataFlow::TYPE_STRING),
                'items_item_comment' => array('_type' => G_CreoleDataFlow::TYPE_STRING),
            )));
        }
    }

?>