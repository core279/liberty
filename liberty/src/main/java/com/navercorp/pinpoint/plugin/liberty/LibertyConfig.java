package com.navercorp.pinpoint.plugin.liberty;

import com.navercorp.pinpoint.bootstrap.config.ExcludePathFilter;
import com.navercorp.pinpoint.bootstrap.config.Filter;
import com.navercorp.pinpoint.bootstrap.config.ProfilerConfig;
import com.navercorp.pinpoint.bootstrap.config.SkipFilter;

import java.util.List;

/**
 * Created by core279 on 2017. 11. 13..
 */
public class LibertyConfig {

    private final boolean libertyEnable;
    private final List<String> libertyBootstrapMains;
    private final Filter<String> libertyExcludeUrlFilter;


    public LibertyConfig(ProfilerConfig config) {
        if (config == null) {
            throw new NullPointerException("config must not be null");
        }

        // plugin
        this.libertyEnable = config.readBoolean("profiler.liberty.enable", true);
        this.libertyBootstrapMains = config.readList("profiler.liberty.bootstrap.main");
        final String libertyExcludeURL = config.readString("profiler.liberty.excludeurl", "");

        if (!libertyExcludeURL.isEmpty()) {
            this.libertyExcludeUrlFilter = new ExcludePathFilter(libertyExcludeURL);
        } else{
            this.libertyExcludeUrlFilter = new SkipFilter<String>();
        }

    }

    public boolean isLibertyEnable() {
        return libertyEnable;
    }

    public List<String> getLibertyBootstrapMains() {
        return libertyBootstrapMains;
    }


    public Filter<String> getLibertyExcludeUrlFilter() {
        return libertyExcludeUrlFilter;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LibertyConfig{");
        sb.append("libertyEnable=").append(libertyEnable);
        sb.append(", libertyBootstrapMains=").append(libertyBootstrapMains);
        sb.append(", libertyExcludeUrlFilter=").append(libertyExcludeUrlFilter);
        sb.append('}');
        return sb.toString();
    }

}
