package dto;

public class Document {
    public String name_document;
    public boolean priority;

    public Document(String name_document, boolean priority){
        this.name_document = name_document;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "[Documento = " + name_document + " com prioridade =" + priority + "]";
    }
}
