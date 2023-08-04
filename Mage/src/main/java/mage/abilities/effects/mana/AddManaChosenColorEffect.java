package mage.abilities.effects.mana;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;

/**
 * @author LevelX2
 */
public class AddManaChosenColorEffect extends ManaEffect {

    private ObjectColor chosenColorInfo = null;

    public AddManaChosenColorEffect() {
        super();
        staticText = "Add one mana of the chosen color";
    }

    protected AddManaChosenColorEffect(final AddManaChosenColorEffect effect) {
        super(effect);
        chosenColorInfo = effect.chosenColorInfo;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            if (color != null) {
                this.chosenColorInfo = color;
                return new Mana(ColoredManaSymbol.lookup(color.toString().charAt(0)));
            }
        }
        return new Mana();
    }

    @Override
    public AddManaChosenColorEffect copy() {
        return new AddManaChosenColorEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        // buggy, but no other way, see comments https://github.com/magefree/mage/commit/cd8d12365f8119dcfe19176a7142e77f80f423b
        return super.getText(mode) + (chosenColorInfo == null ? "" : " {" + chosenColorInfo.toString() + "}");
    }
}
