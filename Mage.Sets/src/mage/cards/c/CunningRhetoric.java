package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CunningRhetoric extends CardImpl {

    public CunningRhetoric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Whenever an opponent attacks you and/or one or more planeswalkers you control, exile the top card of that player's library. You may play that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast it.
        this.addAbility(new CunningRhetoricTriggeredAbility());
    }

    private CunningRhetoric(final CunningRhetoric card) {
        super(card);
    }

    @Override
    public CunningRhetoric copy() {
        return new CunningRhetoric(this);
    }
}

class CunningRhetoricTriggeredAbility extends TriggeredAbilityImpl {

    public CunningRhetoricTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CunningRhetoricEffect(), false);
    }

    private CunningRhetoricTriggeredAbility(final CunningRhetoricTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CunningRhetoricTriggeredAbility copy() {
        return new CunningRhetoricTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID playerId = game.getCombat()
                .getAttackers()
                .stream()
                .filter(attacker -> isControlledBy(game.getCombat().getDefendingPlayerId(attacker, game)))
                .map(game::getControllerId)
                .filter(game.getOpponents(getControllerId())::contains)
                .findFirst()
                .orElse(null);
        if (playerId == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(playerId));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks you and/or one or more planeswalkers you control, " +
                "exile the top card of that player's library. You may play that card for as long " +
                "as it remains exiled, and you may spend mana as though it were mana of any color to cast it.";
    }
}

class CunningRhetoricEffect extends OneShotEffect {

    CunningRhetoricEffect() {
        super(Outcome.Benefit);
    }

    private CunningRhetoricEffect(final CunningRhetoricEffect effect) {
        super(effect);
    }

    @Override
    public CunningRhetoricEffect copy() {
        return new CunningRhetoricEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (opponent == null || sourceObject == null) {
            return false;
        }
        Card card = opponent.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }

        UUID exileZoneId = CardUtil.getExileZoneId(game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game));
        opponent.moveCardsToExile(card, source, game, true, exileZoneId, sourceObject.getIdName());
        CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true);
        return true;
    }
}
