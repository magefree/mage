package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KrenkoBaronOfTinStreet extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOBLIN);

    public KrenkoBaronOfTinStreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}, Sacrifice an artifact: Put a +1/+1 counter on each Goblin you control.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), new TapSourceCost()
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));
        this.addAbility(ability);

        // Whenever an artifact is put into a graveyard from the battlefield, you may pay {R}. If you do, create a 1/1 red Goblin creature token. It gains haste until end of turn.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new DoIfCostPaid(new KrenkoBaronOfTinStreetEffect(), new ManaCostsImpl<>("{R}")),
                false, StaticFilters.FILTER_PERMANENT_ARTIFACT_AN, false
        ));
    }

    private KrenkoBaronOfTinStreet(final KrenkoBaronOfTinStreet card) {
        super(card);
    }

    @Override
    public KrenkoBaronOfTinStreet copy() {
        return new KrenkoBaronOfTinStreet(this);
    }
}

class KrenkoBaronOfTinStreetEffect extends OneShotEffect {

    KrenkoBaronOfTinStreetEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 red Goblin creature token. It gains haste until end of turn";
    }

    private KrenkoBaronOfTinStreetEffect(final KrenkoBaronOfTinStreetEffect effect) {
        super(effect);
    }

    @Override
    public KrenkoBaronOfTinStreetEffect copy() {
        return new KrenkoBaronOfTinStreetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new GoblinToken();
        token.putOntoBattlefield(1, game, source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
