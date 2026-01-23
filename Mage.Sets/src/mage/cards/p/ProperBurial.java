package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author North
 */
public final class ProperBurial extends CardImpl {

    public ProperBurial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");


        // Whenever a creature you control dies, you gain life equal to that creature's toughness.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new GainLifeEffect(ProperBurialValue.instance, "you gain life equal to that creature's toughness"),
            false,
            StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
    }

    private ProperBurial(final ProperBurial card) {
        super(card);
    }

    @Override
    public ProperBurial copy() {
        return new ProperBurial(this);
    }
}

enum ProperBurialValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Permanent) effect.getValue("creatureDied"))
                .map(MageObject::getToughness)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public ProperBurialValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "equal to that creature's toughness";
    }

    @Override
    public String toString() {
        return "equal to that creature's toughness";
    }
}
