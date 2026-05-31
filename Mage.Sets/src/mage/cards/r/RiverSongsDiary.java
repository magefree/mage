package mage.cards.r;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueConditionHint;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.ExileZone;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author padfoothelix 
 */
public final class RiverSongsDiary extends CardImpl {

    public RiverSongsDiary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        

        // Imprint -- Whenever a player casts an instant or sorcery spell from their hand, exile it instead of putting it into a graveyard as it resolves.
	this.addAbility(new RiverSongsDiaryImprintAbility().setAbilityWord(AbilityWord.IMPRINT));

	// At the beginning of your upkeep, if there are four or more cards exiled with River Song's Diary, choose one of them at random. You may cast it without paying its mana cost.
	this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new RiverSongsDiaryCastEffect()
                        .setText("choose one of them at random. You may cast it without paying its mana cost.")
                ).withInterveningIf(RiverSongsDiaryCondition.instance)
                .addHint(RiverSongsDiaryExiledSpellsCount.getHint())
        );
    }

    private RiverSongsDiary(final RiverSongsDiary card) {
        super(card);
    }

    @Override
    public RiverSongsDiary copy() {
        return new RiverSongsDiary(this);
    }
}

enum RiverSongsDiaryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return RiverSongsDiaryExiledSpellsCount.instance.calculate(game, source, null) > 3;

    }

    @Override
    public String toString() {
        return "there are four or more cards exiled with {this}";
    }
}

enum RiverSongsDiaryExiledSpellsCount implements DynamicValue {
    instance;

    private static final Hint hint = new ValueConditionHint(instance, RiverSongsDiaryCondition.instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(
	        game, sourceAbility.getSourceId(), 
                game.getState().getZoneChangeCounter(sourceAbility.getSourceId())
        ));
        if (exileZone != null) {
            return exileZone.size();
        }
	return 0;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "spells exiled with {this}";
    }

    public static Hint getHint() {
        return hint;
    }
}



class RiverSongsDiaryImprintAbility extends TriggeredAbilityImpl {
    	
    public RiverSongsDiaryImprintAbility() {
        super(Zone.BATTLEFIELD, null, false);
        setTriggerPhrase("Whenever a player casts an instant or sorcery spell from their hand" +
                ", exile it instead of putting it into a graveyard as it resolves.");
    }

    private RiverSongsDiaryImprintAbility(final RiverSongsDiaryImprintAbility ability) {
        super(ability);
    }

    @Override
    public RiverSongsDiaryImprintAbility copy() {
        return new RiverSongsDiaryImprintAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getZone() != Zone.HAND) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new RiverSongsDiaryExileEffect(spell, game));
        return true;
    }
}

class RiverSongsDiaryExileEffect extends ReplacementEffectImpl {

    // we store both Spell and Card to work properly on split cards
    private final MageObjectReference morSpell;
    private final MageObjectReference morCard;

    RiverSongsDiaryExileEffect(Spell spell, Game game) {
   	super(Duration.OneUse, Outcome.Benefit);
        this.morSpell = new MageObjectReference(spell.getCard(), game);
        this.morCard = new MageObjectReference(spell.getMainCard(), game);
    }

    private RiverSongsDiaryExileEffect(final RiverSongsDiaryExileEffect effect) {
        super(effect);
        this.morSpell = effect.morSpell;
        this.morCard = effect.morCard;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        Spell sourceSpell = morSpell.getSpell(game);
        if (player == null || sourceSpell == null || sourceSpell.isCopy()) {
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
        return Zone.STACK.equals(zEvent.getFromZone())
                && Zone.GRAVEYARD.equals(zEvent.getToZone())
                && morSpell.refersTo(event.getSourceId(), game) // this is how we check that the spell resolved properly (and was not countered or the like)
                && morCard.refersTo(event.getTargetId(), game); // this is how we check that the card being moved is the one we want.
    }

    @Override
    public RiverSongsDiaryExileEffect copy() {
        return new RiverSongsDiaryExileEffect(this);
    }
}

class RiverSongsDiaryCastEffect extends OneShotEffect {

    RiverSongsDiaryCastEffect() {
        super(Outcome.Benefit);
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
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(exileZone);
        if (player == null || cards.isEmpty()) {
            return false;
        }
	Card randomCard = cards.getRandom(game);
        CardUtil.castSpellWithAttributesForFree(player, source, game, randomCard);
        return true;
    }
}

