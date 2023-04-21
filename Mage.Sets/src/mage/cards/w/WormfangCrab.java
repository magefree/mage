package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */

public final class WormfangCrab extends CardImpl {

    public WormfangCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Wormfang Crab is unblockable.
        this.addAbility(new CantBeBlockedSourceAbility());

        // When Wormfang Crab enters the battlefield, an opponent chooses a permanent you control other than Wormfang Crab and exiles it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WormfangCrabExileEffect(), false));

        // When Wormfang Crab leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false));
    }

    private WormfangCrab(final WormfangCrab card) {
        super(card);
    }

    @Override
    public WormfangCrab copy() {
        return new WormfangCrab(this);
    }
}

class WormfangCrabExileEffect extends OneShotEffect {

    public WormfangCrabExileEffect() {
        super(Outcome.Exile);
        this.staticText = "an opponent chooses a permanent you control other than {this} and exiles it";
    }

    public WormfangCrabExileEffect(final WormfangCrabExileEffect effect) {
        super(effect);
    }

    @Override
    public WormfangCrabExileEffect copy() {
        return new WormfangCrabExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetOpponent targetOpponent = new TargetOpponent(true);
        controller.choose(outcome, targetOpponent, source, game);
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        game.informPlayers(controller.getLogName() + " has selected " + opponent.getLogName() + " to choose a permanent to exile" + CardUtil.getSourceLogName(game, source));
        FilterPermanent filter = new FilterPermanent();
        filter.add(AnotherPredicate.instance);
        filter.add(new ControllerIdPredicate(controller.getId()));
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        target.setTargetName("a permanent that player controls");
        if (!opponent.choose(outcome, target, source, game)) {
            return false;
        }
        Card cardToMove = game.getPermanent(target.getFirstTarget());
        UUID exileId = CardUtil.getExileZoneId(game, source);
        return cardToMove != null && opponent.moveCardsToExile(cardToMove, source, game, true, exileId, CardUtil.getSourceName(game, source));
    }

}
