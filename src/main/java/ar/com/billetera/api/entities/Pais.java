package ar.com.billetera.api.entities;

public class Pais {
    public enum PaisEnum {
        KOSOVO(-665), ARGENTINA(32), COREA_NORTE(408);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private PaisEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static PaisEnum parse(Integer id) {
            PaisEnum status = null; // Default
            for (PaisEnum item : PaisEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

    public enum TipoDocuEnum {
        DNI(10), PASAPORTE(20);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private TipoDocuEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static TipoDocuEnum parse(Integer id) {
            TipoDocuEnum status = null; // Default
            for (TipoDocuEnum item : TipoDocuEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

}
