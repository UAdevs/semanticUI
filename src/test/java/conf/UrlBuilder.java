package conf;

import conf.Env;
import conf.Page;

/**
 * Created by Dev on 14.12.2017.
 */
public class UrlBuilder {
    private String protocol = "https://";
    private String domain = "com";
    private String stageSite ="semantic-ui";
    private String devSite = "";
    private String liveSite = "";
    private String separator = ".";

    public String getUrl(Env env, Page page) {
        String pageString;
        String site;
        switch (page) {
            case TABLE : pageString = Page.TABLE.toString();
            break;
            case CHECKBOX: pageString = Page.CHECKBOX.toString();
            break;
            case DROPDOWN: pageString = Page.DROPDOWN.toString();
            break;
            default: pageString = "";
            break;
        }
        switch (env){
            case DEV: site = devSite;
            break;
            case STAGE: site = stageSite;
            break;
            case LIVE: site = liveSite;
            break;
            default: site = stageSite;
        }
        return protocol + site + separator + domain + pageString;
        }
    }
