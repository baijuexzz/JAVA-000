package day06.singlemode.enumsingle;


public enum EnumSingleMode {
    INSTANCE;

    private void doSomeing(){
        System.out.println(111);
    }

    public static void main(String[] args) {
        String s = EnumSingleMode.INSTANCE.toString();
        System.out.println(s);
    }
}
