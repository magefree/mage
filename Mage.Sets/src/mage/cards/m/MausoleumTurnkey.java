
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class MausoleumTurnkey extends CardImpl {

    public MausoleumTurnkey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Mausoleum Turnkey enters the battlefield, return target creature card of an opponent's choice from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MausoleumTurnkeyEffect(), false));

    }

    private MausoleumTurnkey(final MausoleumTurnkey card) {
        super(card);
    }

    @Override
    public MausoleumTurnkey copy() {
        return new MausoleumTurnkey(this);
    }
}

class MausoleumTurnkeyEffect extends OneShotEffect {

    public MausoleumTurnkeyEffect() {
        super(Outcome.Benefit);
        this.staticText = "return target creature card of an opponent's choice from your graveyard to your hand";
    }

    private MausoleumTurnkeyEffect(final MausoleumTurnkeyEffect effect) {
        super(effect);
    }

    @Override
    public MausoleumTurnkeyEffect copy() {
        return new MausoleumTurnkeyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID opponentId = null;
            if (game.getOpponents(controller.getId()).size() > 1) {
                Target target = new TargetOpponent(true);
                if (controller.chooseTarget(outcome, target, source, game)) {
                    opponentId = target.getFirstTarget();
                }
            } else {
                opponentId = game.getOpponents(controller.getId()).iterator().next();
            }
            if (opponentId != null) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterCreatureCard filter = new FilterCreatureCard("creature card from " + controller.getLogName() + " graveyard");
                    filter.add(new OwnerIdPredicate(controller.getId()));
                    Target target = new TargetCardInGraveyard(filter);
                    opponent.chooseTarget(outcome, target, source, game);
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
