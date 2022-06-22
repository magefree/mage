package mage.cards.n;

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
public final class NightshadeSeer extends CardImpl {

    public NightshadeSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}, {tap}: Reveal any number of black cards in your hand. Target creature gets -X/-X until end of turn, where X is the number of cards revealed this way.
        Ability ability = new SimpleActivatedAbility(new NightshadeSeerEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NightshadeSeer(final NightshadeSeer card) {
        super(card);
    }

    @Override
    public NightshadeSeer copy() {
        return new NightshadeSeer(this);
    }
}

class NightshadeSeerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of black cards in your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public NightshadeSeerEffect() {
        super(Outcome.Detriment);
        this.staticText = "reveal any number of black cards in your hand. "
                + "Target creature gets -X/-X until end of turn, "
                + "where X is the number of cards revealed this way";
    }

    public NightshadeSeerEffect(final NightshadeSeerEffect effect) {
        super(effect);
    }

    @Override
    public NightshadeSeerEffect copy() {
        return new NightshadeSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter));
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = -1 * cost.getNumberRevealedCards();
        game.addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), source);
        return true;
    }
}
