package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BrainInAJar extends CardImpl {

    public BrainInAJar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {T}: Put a charge counter on Brain in a Jar, then you may 
        // cast an instant or sorcery card with converted mana costs equal 
        // to the number of charge counters on Brain in a Jar from your 
        // hand without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new GenericManaCost(1)
        );
        ability.addEffect(new BrainInAJarCastEffect());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}, {T}, Remove X charge counters from Brain in a Jar: Scry X.
        ability = new SimpleActivatedAbility(new BrainInAJarScryEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.CHARGE));
        this.addAbility(ability);
    }

    private BrainInAJar(final BrainInAJar card) {
        super(card);
    }

    @Override
    public BrainInAJar copy() {
        return new BrainInAJar(this);
    }
}

class BrainInAJarCastEffect extends OneShotEffect {

    public BrainInAJarCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = ", then you may cast an instant or sorcery spell "
                + "with mana value equal to the number of charge "
                + "counters on {this} from your hand without paying its mana cost";
    }

    public BrainInAJarCastEffect(final BrainInAJarCastEffect effect) {
        super(effect);
    }

    @Override
    public BrainInAJarCastEffect copy() {
        return new BrainInAJarCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = source.getSourcePermanentOrLKI(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        int counters = sourceObject.getCounters(game).getCount(CounterType.CHARGE);
        FilterCard filter = new FilterInstantOrSorceryCard();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, counters));
        return CardUtil.castSpellWithAttributesForFree(controller, source, game, controller.getHand(), filter);
    }
}

class BrainInAJarScryEffect extends OneShotEffect {

    public BrainInAJarScryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Scry X";
    }

    public BrainInAJarScryEffect(final BrainInAJarScryEffect effect) {
        super(effect);
    }

    @Override
    public BrainInAJarScryEffect copy() {
        return new BrainInAJarScryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int x = RemovedCountersForCostValue.instance.calculate(game, source, this);
            if (x > 0) {
                return controller.scry(x, source, game);
            }
            return true;
        }
        return false;
    }
}
