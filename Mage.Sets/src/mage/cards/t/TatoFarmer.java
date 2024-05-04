package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.card.MilledThisTurnPredicate;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.common.CardsMilledWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TatoFarmer extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("land card in a graveyard that was milled this turn");

    static {
        filter.add(MilledThisTurnPredicate.instance);
    }

    public TatoFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Landfall -- Whenever a land enters the battlefield under your control, you may get two rad counters.
        this.addAbility(new LandfallAbility(
                new AddCountersPlayersEffect(CounterType.RAD.createInstance(2), TargetController.YOU), true
        ));

        // {T}: Put target land card in a graveyard that was milled this turn onto the battlefield under your control tapped.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true),
                new TapSourceCost()
        );
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability, new CardsMilledWatcher());
    }

    private TatoFarmer(final TatoFarmer card) {
        super(card);
    }

    @Override
    public TatoFarmer copy() {
        return new TatoFarmer(this);
    }
}

