package mage.game.permanent.token;

import mage.MageInt;
import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;

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
        this.addAbility(new SimpleStaticAbility(new DaxosSpiritSetPTEffect()));
    }

    private DaxosSpiritToken(final DaxosSpiritToken token) {
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

    protected DaxosSpiritSetPTEffect(final DaxosSpiritSetPTEffect effect) {
        super(effect);
    }

    @Override
    public DaxosSpiritSetPTEffect copy() {
        return new DaxosSpiritSetPTEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Player controller = game.getPlayer(source.getControllerId());
            Permanent permanent = (Permanent) object;
            int amount = controller.getCountersCount(CounterType.EXPERIENCE);
            permanent.getPower().setModifiedBaseValue(amount);
            permanent.getToughness().setModifiedBaseValue(amount);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || !new MageObjectReference(source.getSourceObject(game), game).refersTo(permanent, game)) {
            discard();
            return false;
        }
        affectedObjects.add(permanent);
        return true;
    }
}
