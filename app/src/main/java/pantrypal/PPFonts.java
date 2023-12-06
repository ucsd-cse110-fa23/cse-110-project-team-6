package pantrypal;

import java.io.FileInputStream;

import javafx.scene.text.Font;

enum FF {Itim, KoHo};

// custom fonts
public class PPFonts {
   private static Font Itim = null;
   private static Font KoHo = null;

   public static void loadFonts() {
      try {
         Itim = Font.loadFont(new FileInputStream(Consts.ItimURL), 12);
      } catch (Exception e) {
         System.out.println("ITIM LOAD FAIL");
      }
      try {
         KoHo = Font.loadFont(new FileInputStream(Consts.KoHoURL), 12);
      } catch (Exception e) {
         System.out.println("KOHO LOAD FAIL");
      }
   }

   public static Font makeFont(FF f, int size) {
      switch (f) {
         case Itim:
            if (Itim != null) {
               return Font.font(Itim.getFamily(), size);
            }
            break;
         case KoHo:
            if (KoHo != null) {
               return Font.font(KoHo.getFamily(), size);
            }
            break;
      }
      return Font.font("Verdana", size);
   }
}
