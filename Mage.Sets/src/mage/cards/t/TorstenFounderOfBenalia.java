package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TorstenFounderOfBenalia extends CardImpl {

    public TorstenFounderOfBenalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Torsten, Founder of Benalia enters the battlefield, reveal the top seven cards of your library. Put any number of creature and/or land cards from among them into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TorstenFounderOfBenaliaEffect()));

        // When Torsten dies, create seven 1/1 white Soldier creature tokens.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 7)));
    }

    private TorstenFounderOfBenalia(final TorstenFounderOfBenalia card) {
        super(card);
    }

    @Override
    public TorstenFounderOfBenalia copy() {
        return new TorstenFounderOfBenalia(this);
    }
}

class TorstenFounderOfBenaliaEffect extends OneShotEffect {

    TorstenFounderOfBenaliaEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top seven cards of your library. Put any number of creature and/or land cards " +
                "from among them into your hand and the rest on the bottom of your library in a random order";
    }

    private TorstenFounderOfBenaliaEffect(final TorstenFounderOfBenaliaEffect effect) {
        super(effect);
    }

    @Override
    public TorstenFounderOfBenaliaEffect copy() {
        return new TorstenFounderOfBenaliaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 7));
        player.revealCards(source, cards, game);
        TargetCard target = new TargetCardInLibrary(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURE_OR_LAND
        );
        player.choose(outcome, cards, target, game);
        Cards toHand = new CardsImpl(target.getTargets());
        player.moveCards(toHand, Zone.HAND, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
