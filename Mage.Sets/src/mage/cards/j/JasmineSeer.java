package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class JasmineSeer extends CardImpl {

    public JasmineSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{W}, {tap}: Reveal any number of white cards in your hand. You gain 2 life for each card revealed this way.
        Ability ability = new SimpleActivatedAbility(new JasmineSeerEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private JasmineSeer(final JasmineSeer card) {
        super(card);
    }

    @Override
    public JasmineSeer copy() {
        return new JasmineSeer(this);
    }
}

class JasmineSeerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of white cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public JasmineSeerEffect() {
        super(Outcome.GainLife);
        this.staticText = "reveal any number of white cards in your hand. "
                + "You gain 2 life for each card revealed this way";
    }

    public JasmineSeerEffect(final JasmineSeerEffect effect) {
        super(effect);
    }

    @Override
    public JasmineSeerEffect copy() {
        return new JasmineSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter));
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = cost.getNumberRevealedCards();
        return new GainLifeEffect(2 * xValue).apply(game, source);
    }
}
