package conf;

/**
 * Created by Dev on 14.12.2017.
 */
public enum Page {
    TABLE {
        @Override
        public String toString() {return "/collections/table.html";}
    },
    DROPDOWN {
        @Override
        public String toString () {return "/modules/dropdown.html";}
    },
    CHECKBOX {
        @Override
        public String toString () {return "/modules/checkbox.html";}
    },
}
