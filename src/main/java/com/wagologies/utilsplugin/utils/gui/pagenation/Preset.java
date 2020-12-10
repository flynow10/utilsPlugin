package com.wagologies.utilsplugin.utils.gui.pagenation;

import com.wagologies.utilsplugin.utils.gui.Item;

import java.util.List;

public interface Preset {
    String getName();

    int getSize();

    List<Page> getPages();
}
