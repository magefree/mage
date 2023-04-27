package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class BrineSeer extends CardImpl {

    public BrineSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{U}, {tap}: Reveal any number of blue cards in your hand. Counter target spell unless its controller pays {1} for each card revealed this way.
        Ability ability = new SimpleActivatedAbility(new BrineSeerEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private BrineSeer(final BrineSeer card) {
        super(card);
    }

    @Override
    public BrineSeer copy() {
        return new BrineSeer(this);
    }
}

class BrineSeerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of blue cards in your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public BrineSeerEffect() {
        super(Outcome.Detriment);
        this.staticText = "reveal any number of blue cards in your hand. "
                + "Counter target spell unless its controller pays {1} for each card revealed this way";
    }

    public BrineSeerEffect(final BrineSeerEffect effect) {
        super(effect);
    }

    @Override
    public BrineSeerEffect copy() {
        return new BrineSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter));
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = cost.getNumberRevealedCards();
        return new CounterUnlessPaysEffect(new GenericManaCost(xValue)).apply(game, source);
    }
}
