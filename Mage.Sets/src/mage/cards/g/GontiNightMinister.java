package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author Jmlundeen
 */
public final class GontiNightMinister extends CardImpl {

    public GontiNightMinister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever a player casts a spell they don't own, that player creates a Treasure token.
        this.addAbility(new GontiSpellTriggeredAbility());
        // Whenever a creature deals combat damage to one of your opponents, its controller looks at the top card of that opponent's library and exiles it face down. They may play that card for as long as it remains exiled. Mana of any type can be spent to cast a spell this way.
        this.addAbility(new GontiTriggeredAbility());
    }

    private GontiNightMinister(final GontiNightMinister card) {
        super(card);
    }

    @Override
    public GontiNightMinister copy() {
        return new GontiNightMinister(this);
    }
}

class GontiSpellTriggeredAbility extends TriggeredAbilityImpl {

    public GontiSpellTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenTargetEffect(new TreasureToken()), false);
        setTriggerPhrase("Whenever a player casts a spell they don't own, ");
    }

    private GontiSpellTriggeredAbility(final GontiSpellTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GontiSpellTriggeredAbility copy() {
        return new GontiSpellTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        Spell spell = game.getSpell(event.getTargetId());
        if (player != null && spell != null && !player.getId().equals(spell.getOwnerId())) {
            getEffects().setTargetPointer(new FixedTarget(player.getId()));
            return true;
        }
        return false;
    }
}

class GontiTriggeredAbility extends TriggeredAbilityImpl {

    public GontiTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GontiExileEffect(), false);
        setTriggerPhrase("Whenever a creature deals combat damage to one of your opponents, ");
    }

    private GontiTriggeredAbility(final GontiTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GontiTriggeredAbility copy() {
        return new GontiTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent damagedEvent = (DamagedEvent) event;
        if (damagedEvent.isCombatDamage() && game.getOpponents(controllerId).contains(damagedEvent.getTargetId())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(damagedEvent.getSourceId());
            if (permanent != null && permanent.isCreature(game)) {
                getEffects().setTargetPointer(new FixedTarget(damagedEvent.getTargetId()));
                this.getEffects().setValue("controllerId", permanent.getControllerId());
                return true;
            }
        }
        return false;
    }
}

class GontiExileEffect extends OneShotEffect {

    GontiExileEffect() {
        super(Outcome.Exile);
        staticText = "its controller looks at the top card of that opponent's library and exiles it face down." +
                " They may play that card for as long as it remains exiled. " +
                "Mana of any type can be spent to cast a spell this way.";
    }

    private GontiExileEffect(final GontiExileEffect effect) {
        super(effect);
    }

    @Override
    public GontiExileEffect copy() {
        return new GontiExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer((UUID) source.getEffects().get(0).getValue("controllerId"));
        if (targetOpponent == null || controller == null ) {
            return  false;
        }
        Card card = targetOpponent.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        UUID exileZoneId = CardUtil.getExileZoneId(game, controller.getId(), source.getStackMomentSourceZCC());
        String exileName = CardUtil.getSourceName(game, source) + " - " + controller.getName();
        if (controller.moveCardsToExile(card, source, game, false, exileZoneId, exileName)) {
            card.setFaceDown(true, game);
            CardUtil.makeCardPlayable(game, source, card, false, Duration.Custom, true, controller.getId(), null);
        }
        ContinuousEffect effect = new MayLookAtTargetCardEffect(controller.getId());
        effect.setTargetPointer(new FixedTarget(card.getId()));
        game.addEffect(effect, source);
        return true;
    }
}
