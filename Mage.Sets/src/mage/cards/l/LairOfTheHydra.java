package mage.cards.l;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author weirddan455
 */
public final class LairOfTheHydra extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1, true);

    public LairOfTheHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // If you control two or more other lands, Lair of the Hydra enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldAbility(
                new TapSourceEffect(), condition, "If you control two or more other lands, {this} enters the battlefield tapped.", null
        ));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {X}{G}: Until end of turn, Lair of the Hydra becomes an X/X green Hydra creature. It's still a land. X can't be 0.
        ManaCostsImpl manaCosts = new ManaCostsImpl<>("{X}{G}");
        for (Object cost : manaCosts) {
            if (cost instanceof VariableManaCost) {
                ((VariableManaCost) cost).setMinX(1);
            }
        }
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LairOfTheHydraEffect(), manaCosts));
    }

    private LairOfTheHydra(final LairOfTheHydra card) {
        super(card);
    }

    @Override
    public LairOfTheHydra copy() {
        return new LairOfTheHydra(this);
    }
}

class LairOfTheHydraEffect extends OneShotEffect {

    public LairOfTheHydraEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "Until end of turn, {this} becomes an X/X green Hydra creature. It's still a land. X can't be 0";
    }

    private LairOfTheHydraEffect(final LairOfTheHydraEffect effect) {
        super(effect);
    }

    @Override
    public LairOfTheHydraEffect copy() {
        return new LairOfTheHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        game.addEffect(new BecomesCreatureSourceEffect(
                new CreatureToken(xValue, xValue, "X/X green Hydra creature")
                    .withColor("G")
                    .withSubType(SubType.HYDRA),
                "land", Duration.EndOfTurn), source
        );
        return true;
    }
}
