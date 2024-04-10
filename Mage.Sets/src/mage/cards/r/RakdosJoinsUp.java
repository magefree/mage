package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RakdosJoinsUp extends CardImpl {

    public RakdosJoinsUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Rakdos Joins Up enters the battlefield, return target creature card from your graveyard to the battlefield with two additional +1/+1 counters on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.P1P1.createInstance(2))
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // Whenever a legendary creature you control dies, Rakdos Joins Up deals damage equal to that creature's power to target opponent.
        ability = new DiesCreatureTriggeredAbility(
                new DamageTargetEffect(RakdosJoinsUpValue.instance),
                false, StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RakdosJoinsUp(final RakdosJoinsUp card) {
        super(card);
    }

    @Override
    public RakdosJoinsUp copy() {
        return new RakdosJoinsUp(this);
    }
}

enum RakdosJoinsUpValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(effect.getValue("creatureDied"))
                .map(Permanent.class::cast)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
    }

    @Override
    public RakdosJoinsUpValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that creature's power";
    }

    @Override
    public String toString() {
        return "1";
    }
}
