
package mage.abilities.effects.mana;

import java.util.ArrayList;
import java.util.List;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AddManaAnyColorAttachedControllerEffect extends ManaEffect {

    public AddManaAnyColorAttachedControllerEffect() {
        super();
        staticText = "its controller adds one mana of any color";
    }

    public AddManaAnyColorAttachedControllerEffect(final AddManaAnyColorAttachedControllerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent permanentattachedTo = game.getPermanent(enchantment.getAttachedTo());
            if (permanentattachedTo != null) {
                Player player = game.getPlayer(permanentattachedTo.getControllerId());
                if (player != null) {
                    checkToFirePossibleEvents(getMana(game, source), game, source);
                    player.getManaPool().addMana(getMana(game, source), game, source);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AddManaAnyColorAttachedControllerEffect copy() {
        return new AddManaAnyColorAttachedControllerEffect(this);
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if (land != null) {
                Player player = game.getPlayer(land.getControllerId());
                ChoiceColor choice = new ChoiceColor();
                if (player != null && player.choose(outcome, choice, game)) {
                    return choice.getMana(1);
                }
            }
        }
        return new Mana();
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        ArrayList<Mana> netMana = new ArrayList<>();
        netMana.add(Mana.GreenMana(1));
        netMana.add(Mana.WhiteMana(1));
        netMana.add(Mana.BlueMana(1));
        netMana.add(Mana.RedMana(1));
        netMana.add(Mana.BlackMana(1));
        return netMana;
    }

}
