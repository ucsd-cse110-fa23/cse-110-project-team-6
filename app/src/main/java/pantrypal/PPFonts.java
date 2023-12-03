package pantrypal;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;

enum FF {Itim, KoHo};

public class PPFonts {
   private static Font Itim = null;
   private static Font KoHo = null;

   // private static Font F65 = Font.font("Verdana", 65);
   // private static Font F40 = Font.font("Verdana", 40);
   // private static Font F30 = Font.font("Verdana", 30);
   // private static Font F20 = Font.font("Verdana", 20);
   // private static Font F15 = Font.font("Verdana", 15);
   // private static Font F12 = Font.font("Verdana", 12);

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

   // public static Font F65(FontFamily f) {
   //    return makeFont(f, 65);
   // }

   // public static Font F40(FontFamily f) {
   //    return makeFont(f, 40);
   // }

   // public static Font F30(FontFamily f) {
   //    return makeFont(f, 30);
   // }

   // public static Font F20(FontFamily f) {
   //    return makeFont(f, 20);
   // }

   // public static Font F15(FontFamily f) {
   //    return makeFont(f, 15);
   // }

   // public static Font F12(FontFamily f) {
   //    return makeFont(f, 12);
   // }
}
