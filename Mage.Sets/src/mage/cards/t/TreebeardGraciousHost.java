package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreebeardGraciousHost extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Halfling or Treefolk");

    static {
        filter.add(Predicates.or(
                SubType.HALFLING.getPredicate(),
                SubType.TREEFOLK.getPredicate()
        ));
    }

    public TreebeardGraciousHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // When Treebeard, Gracious Host enters the battlefield, create two Food tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken(), 2)));

        // Whenever you gain life, put that many +1/+1 counters on target Halfling or Treefolk.
        Ability ability = new GainLifeControllerTriggeredAbility(new TreebeardGraciousHostEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TreebeardGraciousHost(final TreebeardGraciousHost card) {
        super(card);
    }

    @Override
    public TreebeardGraciousHost copy() {
        return new TreebeardGraciousHost(this);
    }
}

class TreebeardGraciousHostEffect extends OneShotEffect {

    TreebeardGraciousHostEffect() {
        super(Outcome.Benefit);
        staticText = "put that many +1/+1 counters on target Halfling or Treefolk";
    }

    private TreebeardGraciousHostEffect(final TreebeardGraciousHostEffect effect) {
        super(effect);
    }

    @Override
    public TreebeardGraciousHostEffect copy() {
        return new TreebeardGraciousHostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        int amount = (Integer) getValue("gainedLife");
        return permanent != null && amount > 0
                && permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
    }
}
