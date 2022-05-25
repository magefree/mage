package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NineFingersKeene extends CardImpl {

    public NineFingersKeene(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Ward—Pay 9 life.
        this.addAbility(new WardAbility(new PayLifeCost(9), false));

        // Whenever Nine-Fingers Keene deals combat damage to a player, look at the top nine cards of your library. You may put a Gate card from among them onto the battlefield. Then if you control nine or more Gates, put the rest into your hand. Otherwise, put the rest on the bottom of your library in a random order.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new NineFingersKeeneEffect(), false));
    }

    private NineFingersKeene(final NineFingersKeene card) {
        super(card);
    }

    @Override
    public NineFingersKeene copy() {
        return new NineFingersKeene(this);
    }
}

class NineFingersKeeneEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a Gate card");
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.GATE);

    static {
        filter.add(SubType.GATE.getPredicate());
    }

    NineFingersKeeneEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top nine cards of your library. You may put a Gate card " +
                "from among them onto the battlefield. Then if you control nine or more Gates, " +
                "put the rest into your hand. Otherwise, put the rest on the bottom of your library in a random order";
    }

    private NineFingersKeeneEffect(final NineFingersKeeneEffect effect) {
        super(effect);
    }

    @Override
    public NineFingersKeeneEffect copy() {
        return new NineFingersKeeneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 9));
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary(0, 1, filter);
        player.choose(outcome, cards, target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        if (game.getBattlefield().count(filter2, source.getControllerId(), source, game) >= 9) {
            player.moveCards(cards, Zone.HAND, source, game);
        } else {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }
}
