package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MutagenToken;
import mage.target.common.TargetCardInGraveyard;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheOoze extends CardImpl {

    public TheOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever a creature you control with a +1/+1 counter on it leaves the battlefield, create a Mutagen token for each +1/+1 counter on it.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new MutagenToken(), TheOozeValue.instance),
                false, StaticFilters.FILTER_A_CONTROLLED_CREATURE_P1P1
        ));

        // {T}: Exile target card from a graveyard. Create a Mutagen token.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect(), new TapSourceCost());
        ability.addEffect(new CreateTokenEffect(new MutagenToken()));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private TheOoze(final TheOoze card) {
        super(card);
    }

    @Override
    public TheOoze copy() {
        return new TheOoze(this);
    }
}

enum TheOozeValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Permanent) effect.getValue("creatureDied"))
                .map(permanent -> permanent.getCounters(game).getCount(CounterType.P1P1))
                .orElse(0);
    }

    @Override
    public TheOozeValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "+1/+1 counter on it";
    }

    @Override
    public String toString() {
        return "1";
    }
}
