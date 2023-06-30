package mage.game.permanent.token;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author spjspj
 */
public final class DaxosSpiritToken extends TokenImpl {

    public DaxosSpiritToken() {
        super("Spirit Token", "white and black Spirit enchantment creature token with \"This creature's power and toughness are each equal to the number of experience counters you have.\"");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(0);
        toughness = new MageInt(0);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DaxosSpiritSetPTEffect()));
    }

    public DaxosSpiritToken(final DaxosSpiritToken token) {
        super(token);
    }

    public DaxosSpiritToken copy() {
        return new DaxosSpiritToken(this);
    }
}

class DaxosSpiritSetPTEffect extends ContinuousEffectImpl {

    public DaxosSpiritSetPTEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "This creature's power and toughness are each equal to the number of experience counters you have";
    }

    public DaxosSpiritSetPTEffect(final DaxosSpiritSetPTEffect effect) {
        super(effect);
    }

    @Override
    public DaxosSpiritSetPTEffect copy() {
        return new DaxosSpiritSetPTEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || !new MageObjectReference(source.getSourceObject(game), game).refersTo(permanent, game)) {
            discard();
            return false;
        }

        int amount = controller.getCounters().getCount(CounterType.EXPERIENCE);
        permanent.getPower().setModifiedBaseValue(amount);
        permanent.getToughness().setModifiedBaseValue(amount);
        return true;
    }
}
