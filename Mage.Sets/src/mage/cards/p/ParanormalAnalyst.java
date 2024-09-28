package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManifestedDreadEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ParanormalAnalyst extends CardImpl {

    public ParanormalAnalyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you manifest dread, put a card you put into your graveyard this way into your hand.
        this.addAbility(new ParanormalAnalystTriggeredAbility());
    }

    private ParanormalAnalyst(final ParanormalAnalyst card) {
        super(card);
    }

    @Override
    public ParanormalAnalyst copy() {
        return new ParanormalAnalyst(this);
    }
}

class ParanormalAnalystTriggeredAbility extends TriggeredAbilityImpl {

    ParanormalAnalystTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ParanormalAnalystEffect());
        setTriggerPhrase("Whenever you manifest dread, ");
    }

    private ParanormalAnalystTriggeredAbility(final ParanormalAnalystTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ParanormalAnalystTriggeredAbility copy() {
        return new ParanormalAnalystTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANIFESTED_DREAD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTargets(((ManifestedDreadEvent) event).getGraveyardCards()));
        return true;
    }
}

class ParanormalAnalystEffect extends OneShotEffect {

    ParanormalAnalystEffect() {
        super(Outcome.Benefit);
        staticText = "put a card you put into your graveyard this way into your hand";
    }

    private ParanormalAnalystEffect(final ParanormalAnalystEffect effect) {
        super(effect);
    }

    @Override
    public ParanormalAnalystEffect copy() {
        return new ParanormalAnalystEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        cards.retainZone(Zone.GRAVEYARD, game);
        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInYourGraveyard();
                target.withNotTarget(true);
                player.choose(outcome, cards, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        return player.moveCards(card, Zone.HAND, source, game);
    }
}
