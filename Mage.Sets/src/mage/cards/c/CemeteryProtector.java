package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HumanToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class CemeteryProtector extends CardImpl {

    public CemeteryProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Cemetery Protector enters the battlefield, exile a card from a graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CemeteryProtectorEffect()));

        // Whenever you play a land or cast a spell, if it shares a card type with the exiled card, create a 1/1 white Human creature token.
        this.addAbility(new CemeteryProtectorTriggeredAbility());
    }

    private CemeteryProtector(final CemeteryProtector card) {
        super(card);
    }

    @Override
    public CemeteryProtector copy() {
        return new CemeteryProtector(this);
    }
}

class CemeteryProtectorEffect extends OneShotEffect {

    public CemeteryProtectorEffect() {
        super(Outcome.Exile);
        staticText = "exile a card from a graveyard";
    }

    private CemeteryProtectorEffect(final CemeteryProtectorEffect effect) {
        super(effect);
    }

    @Override
    public CemeteryProtectorEffect copy() {
        return new CemeteryProtectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInGraveyard target = new TargetCardInGraveyard();
            target.setNotTarget(true);
            controller.choose(outcome, target, source.getSourceId(), game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                MageObject sourceObject = source.getSourceObject(game);
                String exileName = sourceObject == null ? null : sourceObject.getIdName();
                return controller.moveCardsToExile(card, source, game, true, exileId, exileName);
            }
        }
        return false;
    }
}

class CemeteryProtectorTriggeredAbility extends TriggeredAbilityImpl {

    public CemeteryProtectorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new HumanToken()));
    }

    private CemeteryProtectorTriggeredAbility(final CemeteryProtectorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CemeteryProtectorTriggeredAbility copy() {
        return new CemeteryProtectorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId)) {
            Effect effect = getEffects().get(0);
            effect.setValue("targetId", event.getTargetId());
            effect.setValue("eventType", event.getType());
            // sourceObjectZoneChangeCounter is not updated until after checkTrigger is called.  Get ZCC from GameState instead.
            effect.setValue("exileId", CardUtil.getExileZoneId(game, sourceId, game.getState().getZoneChangeCounter(sourceId)));
            return true;
       }
        return false;
    }

    private boolean checkCardTypes(List<CardType> playedCardTypes, UUID exileId, Game game) {
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone != null) {
            for (UUID cardId : exileZone) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    for (CardType exileCardType : card.getCardType(game)) {
                        if (playedCardTypes.contains(exileCardType)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Effect effect = getEffects().get(0);
        UUID targetId = (UUID) effect.getValue("targetId");
        GameEvent.EventType eventType = (GameEvent.EventType) effect.getValue("eventType");
        UUID exileId = (UUID) effect.getValue("exileId");
        if (targetId != null && eventType != null && exileId != null) {
            if (eventType == GameEvent.EventType.LAND_PLAYED) {
                Permanent permanent = game.getPermanent(targetId);
                return permanent != null && checkCardTypes(permanent.getCardType(game), exileId, game);
            } else if (eventType == GameEvent.EventType.SPELL_CAST) {
                Spell spell = game.getSpellOrLKIStack(targetId);
                return spell != null && checkCardTypes(spell.getCardType(game), exileId, game);
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you play a land or cast a spell, if it shares a card type with the exiled card, ";
    }
}
