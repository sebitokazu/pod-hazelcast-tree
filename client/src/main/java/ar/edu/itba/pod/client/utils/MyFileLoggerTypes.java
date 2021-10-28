package ar.edu.itba.pod.client.utils;

public enum MyFileLoggerTypes {
    MAP_REDUCE_START {
        @Override
        public String toString() {
            return "Inicio del trabajo map/reduce";
        }
    },
    MAP_REDUCE_END {
        @Override
        public String toString() {
            return "Fin del trabajo map/reduce";
        }
    },
    PARSE_CSV_START {
        @Override
        public String toString() {
            return "Inicio de la lectura del archivo";
        }
    },
    PARSE_CSV_END {
        @Override
        public String toString() {
            return "Fin de la lectura del archivo";
        }
    };
}
