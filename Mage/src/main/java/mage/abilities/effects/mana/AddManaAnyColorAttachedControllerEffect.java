package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LevelX2
 */
public class AddManaAnyColorAttachedControllerEffect extends ManaEffect {

    public AddManaAnyColorAttachedControllerEffect() {
        super();
        staticText = "its controller adds an additional one mana of any color";
    }

    public AddManaAnyColorAttachedControllerEffect(final AddManaAnyColorAttachedControllerEffect effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent permanentAttachedTo = game.getPermanent(enchantment.getAttachedTo());
            if (permanentAttachedTo != null) {
                return game.getPlayer(permanentAttachedTo.getControllerId());
            }
        }
        return null;
    }

    @Override
    public AddManaAnyColorAttachedControllerEffect copy() {
        return new AddManaAnyColorAttachedControllerEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(Mana.AnyMana(1));
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
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
        }
        return new Mana();
    }
}
