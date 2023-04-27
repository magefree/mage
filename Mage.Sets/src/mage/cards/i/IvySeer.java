package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class IvySeer extends CardImpl {

    public IvySeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{G}, {tap}: Reveal any number of green cards in your hand. Target creature gets +X/+X until end of turn, where X is the number of cards revealed this way.
        Ability ability = new SimpleActivatedAbility(new IvySeerEffect(), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private IvySeer(final IvySeer card) {
        super(card);
    }

    @Override
    public IvySeer copy() {
        return new IvySeer(this);
    }
}

class IvySeerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of green cards in your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public IvySeerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "reveal any number of green cards in your hand. "
                + "Target creature gets +X/+X until end of turn, where X is the number of cards revealed this way";
    }

    public IvySeerEffect(final IvySeerEffect effect) {
        super(effect);
    }

    @Override
    public IvySeerEffect copy() {
        return new IvySeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter));
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = cost.getNumberRevealedCards();
        game.addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), source);
        return true;
    }
}
