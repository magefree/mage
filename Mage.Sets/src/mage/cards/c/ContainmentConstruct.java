package mage.cards.c;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author jeffwadsworth
 */
public final class ContainmentConstruct extends CardImpl {

    public ContainmentConstruct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you discard a card, you may exile that card from your graveyard. If you do, you may play that card this turn.
        this.addAbility(new ContainmentConstructTriggeredAbility());

    }

    private ContainmentConstruct(final ContainmentConstruct card) {
        super(card);
    }

    @Override
    public ContainmentConstruct copy() {
        return new ContainmentConstruct(this);
    }
}

class ContainmentConstructTriggeredAbility extends TriggeredAbilityImpl {

    public ContainmentConstructTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ContainmentConstructEffect(), false);
        setTriggerPhrase("Whenever you discard a card, ");
    }

    public ContainmentConstructTriggeredAbility(final ContainmentConstructTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ContainmentConstructTriggeredAbility copy() {
        return new ContainmentConstructTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId() == this.getControllerId()
                && game.getState().getZone(event.getTargetId()) == Zone.GRAVEYARD) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }
}

class ContainmentConstructEffect extends OneShotEffect {

    public ContainmentConstructEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile that card from your graveyard. If you do, you may play that card this turn";
    }

    public ContainmentConstructEffect(final ContainmentConstructEffect effect) {
        super(effect);
    }

    @Override
    public ContainmentConstructEffect copy() {
        return new ContainmentConstructEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card discardedCard = game.getCard(targetPointer.getFirst(game, source));
        Card containmentConstruct = game.getCard(source.getSourceId());
        if (discardedCard != null
                && containmentConstruct != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller.chooseUse(Outcome.Benefit, "Do you want to exile the discarded card?  You may play it this turn from exile.", source, game)) {
                UUID exileId = CardUtil.getExileZoneId(game, source);
                controller.moveCardsToExile(discardedCard, source, game, true, exileId, "Exiled by " + containmentConstruct.getIdName());
                PlayFromNotOwnHandZoneTargetEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(discardedCard.getId(), game));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}
