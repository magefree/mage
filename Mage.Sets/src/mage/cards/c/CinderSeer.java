package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class CinderSeer extends CardImpl {

    public CinderSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{R}, {tap}: Reveal any number of red cards in your hand. Cinder Seer deals X damage to any target, where X is the number of cards revealed this way.
        Ability ability = new SimpleActivatedAbility(new CinderSeerEffect(), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private CinderSeer(final CinderSeer card) {
        super(card);
    }

    @Override
    public CinderSeer copy() {
        return new CinderSeer(this);
    }
}

class CinderSeerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of red cards in your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public CinderSeerEffect() {
        super(Outcome.Damage);
        this.staticText = "reveal any number of red cards in your hand. "
                + "{this} deals X damage to any target, where X is the number of cards revealed this way";
    }

    public CinderSeerEffect(final CinderSeerEffect effect) {
        super(effect);
    }

    @Override
    public CinderSeerEffect copy() {
        return new CinderSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter));
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = cost.getNumberRevealedCards();
        return new DamageTargetEffect(xValue).apply(game, source);
    }
}
