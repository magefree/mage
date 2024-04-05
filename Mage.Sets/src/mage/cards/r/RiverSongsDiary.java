package mage.cards.r;

import java.util.UUID;

import mage.ApprovingObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.*;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Skiwkr
 */
public final class RiverSongsDiary extends CardImpl {

    public RiverSongsDiary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        

        // Imprint -- Whenever a player casts an instant or sorcery spell from their hand, exile it instead of putting it into a graveyard as it resolves.
        this.addAbility(new RiverSongsDiaryTriggeredAbility());
        // At the beginning of your upkeep, if there are four or more cards exiled with River Song's Diary, choose one of them at random. You may cast it without paying its mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new RiverSongsDiaryCastEffect(), TargetController.YOU, false));
    }

    private RiverSongsDiary(final RiverSongsDiary card) {
        super(card);
    }

    @Override
    public RiverSongsDiary copy() {
        return new RiverSongsDiary(this);
    }
}

class RiverSongsDiaryTriggeredAbility extends TriggeredAbilityImpl {

    RiverSongsDiaryTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private RiverSongsDiaryTriggeredAbility(final RiverSongsDiaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RiverSongsDiaryTriggeredAbility copy() {
        return new RiverSongsDiaryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        System.out.println(spell.getFromZone() + "fromZone");
        if (spell == null || !spell.isInstantOrSorcery(game) || !spell.getFromZone().equals(Zone.HAND)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new RiverSongsDiaryExileEffect(spell, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an instant or sorcery spell from their hand, " +
                "exile it instead of putting it into a graveyard as it resolves.";
    }
}

class RiverSongsDiaryExileEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    RiverSongsDiaryExileEffect(Spell spell, Game game) {
        super(Duration.WhileOnStack, Outcome.Benefit);
        this.mor = new MageObjectReference(spell, game);
    }

    private RiverSongsDiaryExileEffect(final RiverSongsDiaryExileEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getStack().getSpell(event.getTargetId());
        if (sourceSpell == null || sourceSpell.isCopy()) {
            return false;
        }
        Player player = game.getPlayer(sourceSpell.getOwnerId());
        if (player == null) {
            return false;
        }
        player.moveCardsToExile(
                sourceSpell, source, game, false,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        if (zEvent.getFromZone() != Zone.STACK
                || zEvent.getToZone() != Zone.GRAVEYARD
                || event.getSourceId() == null
                || !event.getSourceId().equals(event.getTargetId())
                || mor.getZoneChangeCounter() != game.getState().getZoneChangeCounter(event.getSourceId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(mor.getSourceId());
        return spell != null && spell.isInstantOrSorcery(game) && spell.getFromZone().equals(Zone.HAND);
    }

    @Override
    public RiverSongsDiaryExileEffect copy() {
        return new RiverSongsDiaryExileEffect(this);
    }
}

class RiverSongsDiaryCastEffect extends OneShotEffect {

    RiverSongsDiaryCastEffect() {
        super(Outcome.Benefit);
        staticText = "if there are four or more cards exiled with River Song's Diary, " +
                "choose one of them at random. You may cast it without paying its mana cost.";
    }

    private RiverSongsDiaryCastEffect(final RiverSongsDiaryCastEffect effect) {
        super(effect);
    }

    @Override
    public RiverSongsDiaryCastEffect copy() {
        return new RiverSongsDiaryCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (exileZone == null || controller == null || exileZone.isEmpty()) {
            return false;
        }

        Cards cards = new CardsImpl(exileZone);
        if (player == null || cards.isEmpty()) {
            return false;
        }
        if(cards.size()>4) {
            Card randomCard = cards.getRandom(game);
            if (controller.chooseUse(outcome, "Cast " + randomCard.getLogName()
                    + " without paying its mana cost from exile?", source, game)) {
                game.getState().setValue("PlayFromNotOwnHandZone" + randomCard.getId(), Boolean.TRUE);
                controller.cast(controller.chooseAbilityForCast(randomCard, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + randomCard.getId(), null);
            }
        }
        return true;
    }
}