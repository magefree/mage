package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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

    private UUID idToCheck = null;
    private GameEvent.EventType eventToCheck = null;
    private UUID exileZoneId = null;

    public CemeteryProtectorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new HumanToken()));
    }

    private CemeteryProtectorTriggeredAbility(final CemeteryProtectorTriggeredAbility ability) {
        super(ability);
        idToCheck = ability.idToCheck;
        eventToCheck = ability.eventToCheck;
        exileZoneId = ability.exileZoneId;
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
            idToCheck = event.getTargetId();
            eventToCheck = event.getType();
            // TODO: sourceObjectZoneChangeCounter is incorrect here (always reporting 0).  Workaround is to get ZCC from GameState.
            // May need research as to why this variable isn't reporting correctly.
            exileZoneId = CardUtil.getExileZoneId(game, sourceId, game.getState().getZoneChangeCounter(sourceId));
            return true;
       }
        return false;
    }

    private boolean checkCardTypes(List<CardType> playedCardTypes, Game game) {
        if (exileZoneId != null) {
            ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
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
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        if (eventToCheck == GameEvent.EventType.LAND_PLAYED) {
            Permanent permanent = game.getPermanent(idToCheck);
            return permanent != null && checkCardTypes(permanent.getCardType(game), game);
        } else if (eventToCheck == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getSpellOrLKIStack(idToCheck);
            return spell != null && checkCardTypes(spell.getCardType(game), game);
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you play a land or cast a spell, if it shares a card type with the exiled card, ";
    }
}
