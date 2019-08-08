package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoadOfReturn extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public RoadOfReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}");

        // Choose one —
        // • Return target permanent card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // • Put your commander into your hand from the command zone.
        this.getSpellAbility().addMode(new Mode(new RoadOfReturnEffect()));

        // Entwine {2}
        this.addAbility(new EntwineAbility("{2}"));
    }

    private RoadOfReturn(final RoadOfReturn card) {
        super(card);
    }

    @Override
    public RoadOfReturn copy() {
        return new RoadOfReturn(this);
    }
}

class RoadOfReturnEffect extends OneShotEffect {

    RoadOfReturnEffect() {
        super(Outcome.Benefit);
        staticText = "Put your commander into your hand from the command zone.";
    }

    private RoadOfReturnEffect(final RoadOfReturnEffect effect) {
        super(effect);
    }

    @Override
    public RoadOfReturnEffect copy() {
        return new RoadOfReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Card> commandersInCommandZone = new ArrayList<>(1);
        game.getCommandersIds(
                controller, CommanderCardType.COMMANDER_OR_OATHBREAKER
        ).stream().forEach(commanderId -> {
            Card commander = game.getCard(commanderId);
            if (commander != null && game.getState().getZone(commander.getId()) == Zone.COMMAND) {
                commandersInCommandZone.add(commander);
            }
        });
        if (commandersInCommandZone.size() == 1) {
            controller.moveCards(commandersInCommandZone.get(0), Zone.HAND, source, game);
        } else if (commandersInCommandZone.size() == 2) {
            Card firstCommander = commandersInCommandZone.get(0);
            Card secondCommander = commandersInCommandZone.get(1);
            if (controller.chooseUse(Outcome.ReturnToHand, "Return which commander to hand?", null, firstCommander.getName(), secondCommander.getName(), source, game)) {
                controller.moveCards(firstCommander, Zone.HAND, source, game);
            } else {
                controller.moveCards(secondCommander, Zone.HAND, source, game);
            }
        }
        return true;
    }
}