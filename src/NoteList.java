/**
 * Klasa reprezentująca listę notatek. Przyjmuje obiekty klasy Note i jej subklas.
 *
 * @version 1.0
 * @author Michał Mikuła
 */
public class NoteList {
    static int FULL = 0;
    static int HIDDEN = 1;
    static int PUBLIC = 2;
    private Note[] noteList;

    /**
     * Metoda zwracająca listę notatek.
     * @return Lista notatek.
     */
    public Note[] getNoteList(){
        return this.noteList;
    }

    /**
     * Metoda zwracająca pojedyńczą notatkę z listy.
     * @param index Pozycja notatki na liście.
     * @return Notatka z listy notatek.
     */
    public Note getNote(int index){
        return this.noteList[index];
    }

    /**
     * Metoda zwracająca długość listy notatek.
     * @return Długość listy notatek.
     */
    public int getListLength(){
        return this.getNoteList().length;
    }

    /**
     * Metoda przypisująca nową listę notatek.
     * @param noteList Nowa lista notatek.
     */
    public void setNoteList(Note[] noteList){
        this.noteList = noteList;
    }

    /**
     * Metoda przypisująca nową wartość do wyznaczonej notatki.
     * @param note Nowa notatka
     * @param index Pozycja na liście
     */
    public void setNote(Note note, int index){
        this.noteList[index] = note;
    }

    /**
     * Metoda poszerzająca listę notatek o podaną ilość miejsc
     * @param slots Ilość miejsc, o którą zostanie poszerzona lista notatek.
     */
    private void expandNoteList(int slots){
        Note[] newList = new Note[this.getNoteList().length + slots];
        for(int i = 0; i < newList.length; i++){
            if(i < this.getNoteList().length) newList[i] = this.getNote(i);
            else newList[i] = new Note();
        }
        this.setNoteList(newList);
    }

    /**
     * Metoda zwracająca pozycję notatki na liście. Jeśli notatka nie znajduje się na liście, metoda zwraca wartość ujemną.
     * @param note Notatka, której pozycję na liście chcemy uzyskać.
     * @return Pozycja notatki na liście lub wartość ujemna w przypadku braku notatki na liście.
     */
    public int getNoteIndex(Note note) {
        for(int i = 0; i < this.getListLength(); i++){
            if(this.getNote(i).equals(note)){
                return i;
            }
        }
        return -1;
    }


    /**
     * Metoda dodająca do listy notatek nową notatkę. Zawsze dodawana jest na koniec listy.
     * @param note Notatka do dodania na listę.
     */
    public void addNote(Note note){
        int curLength = this.getNoteList().length;
        this.expandNoteList(1);
        System.arraycopy(new Note[]{note}, 0, this.getNoteList(), curLength, 1);
    }

    /**
     * Metoda dodająca do listy notatek nowe notatki. Zawsze dodawane są na koniec listy.
     * @param notes Lista notatek do dodania.
     */
    public void addNote(Note[] notes){
        int curLength = this.getNoteList().length;
        this.expandNoteList(notes.length);
        System.arraycopy(notes, 0, this.getNoteList(), curLength, notes.length);
    }

    /**
     * Metoda kasująca notatki na wyznaczonych pozycjach listy.
     * @param indexes Lista pozycji, na których znajdują się notatki do skasowania.
     */
    public void removeNote(int[] indexes){
        Note[] newList = new Note[this.getNoteList().length - indexes.length];
        Integer[] acceptedIndexes = new Integer[this.getNoteList().length - indexes.length];
        int cnt = 0;
        for(int i = 0; i < this.getNoteList().length; i++){
            boolean isAccepted = true;
            for(int j = 0; j < indexes.length; j++){
                if(i == indexes[j]){
                    isAccepted = false;
                    break;
                }
                if(isAccepted){
                    acceptedIndexes[cnt] = i;
                    cnt++;
                }
            }
        }
        for(int i = 0; i < newList.length; i++){
            newList[i] = this.getNote(acceptedIndexes[i]);
        }
        this.setNoteList(newList);
    }

    /**
     * Metoda kasująca notatkę znajdującą się na wyznaczonym miejscu listy.
     * @param index Numer pozycji notatki na liście.
     */
    public void removeNote(int index){
        Note[] newList = new Note[this.getNoteList().length - 1];
        Integer[] acceptedIndexes = new Integer[this.getNoteList().length - 1];
        int cnt = 0;
        for(int i = 0; i < this.getNoteList().length; i++){
            boolean isAccepted = true;
            if(i == index){
                isAccepted = false;
            }
            if(isAccepted){
                acceptedIndexes[cnt] = i;
                cnt++;
            }
        }
        for(int i = 0; i < newList.length; i++){
            newList[i] = this.getNote(acceptedIndexes[i]);
        }
        this.setNoteList(newList);
    }

    /**
     * Konstruktor domyślny. Tworzy nową, pustą listę notatek.
     */
    NoteList(){
        this.setNoteList(
                new Note[0]
        );
    }

    /**
     * Konstruktor parametryczny. Tworzy nową listę notatek opartą o podany zbiór notatek.
     * @param noteList Zbiór notatek.
     * @param list_mode Tryb przypisania listy. (Pełna = 0, tylko ukryte = 1, tylko jawne = 2)
     */
    NoteList(Note[] noteList, int list_mode) {
        if (list_mode == HIDDEN) {
            int hidden_count = 0;
            for (int i = 0; i < noteList.length; i++) {
                if (noteList[i].getHidden()) {
                    hidden_count++;
                }
            }

            Note[] hidden = new Note[hidden_count];
            int cnt = 0;
            for (int i = 0; i < noteList.length; i++) {
                if (noteList[i].getHidden()) {
                    hidden[cnt] = noteList[i];
                    cnt++;
                }
            }

            this.setNoteList(hidden);
        } else if(list_mode == PUBLIC){
            int public_count = 0;
            for (int i = 0; i < noteList.length; i++) {
                if (!noteList[i].getHidden()) {
                    public_count++;
                }
            }

            Note[] public_list = new Note[public_count];
            int cnt = 0;
            for (int i = 0; i < noteList.length; i++) {
                if (!noteList[i].getHidden()) {
                    public_list[cnt] = noteList[i];
                    cnt++;
                }
            }

            this.setNoteList(public_list);
        } else if(list_mode == FULL){
            this.setNoteList(noteList);
        }
    }
}
