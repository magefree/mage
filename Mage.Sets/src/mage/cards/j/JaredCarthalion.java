package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KavuAllColorToken;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class JaredCarthalion extends CardImpl {

    private static final FilterCard filter = new FilterCard("multicolored card from your graveyard");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public JaredCarthalion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{W}{U}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JARED);

        this.setStartingLoyalty(5);

        // +1: Create a 3/3 Kavu creature token with trample that's all colors.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new KavuAllColorToken()), 1));

        // −3: Choose up to two target creatures. For each of them, put a number of +1/+1 counters on it equal to the number of colors it is.
        Ability ability1 = new LoyaltyAbility(new JaredCarthalionBoostEffect(), -3);
        ability1.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability1);

        // −6: Return target multicolored card from your graveyard to your hand. If that card was all colors, draw a card and create two Treasure tokens.
        Ability ability2 = new LoyaltyAbility(new JaredCarthalionUltimateEffect(), -6);
        ability2.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability2);

        // Jared Carthalion can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private JaredCarthalion(final JaredCarthalion card) {
        super(card);
    }

    @Override
    public JaredCarthalion copy() {
        return new JaredCarthalion(this);
    }
}

class JaredCarthalionBoostEffect extends OneShotEffect {

    public JaredCarthalionBoostEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Choose up to two target creatures. For each of them, put " +
                "a number of +1/+1 counters on it equal to the number of colors it is.";
    }

    public JaredCarthalionBoostEffect(final JaredCarthalionBoostEffect effect) {
        super(effect);
    }

    @Override
    public JaredCarthalionBoostEffect copy() {
        return new JaredCarthalionBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent creature = game.getPermanent(targetId);
            if (creature != null && creature.getColor().getColorCount() != 0) {
                Counter counter = CounterType.P1P1.createInstance(creature.getColor().getColorCount());
                creature.addCounters(counter, source, game);
            }
        }
        return true;
    }
}

class JaredCarthalionUltimateEffect extends OneShotEffect {

    public JaredCarthalionUltimateEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return target multicolored card from your graveyard to your hand. " +
                "If that card was all colors, draw a card and create two Treasure tokens.";
    }

    public JaredCarthalionUltimateEffect(final JaredCarthalionUltimateEffect effect) {
        super(effect);
    }

    @Override
    public JaredCarthalionUltimateEffect copy() {
        return new JaredCarthalionUltimateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (controller != null && card != null) {
            if (controller.moveCards(card, Zone.HAND, source, game)
                    && card.getColor().getColorCount() == 5) {
                controller.drawCards(1, source, game);
                new TreasureToken().putOntoBattlefield(2, game, source);
                return true;
            }
        }
        return false;
    }
}
