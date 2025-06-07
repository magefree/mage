package mage.cards.t;

import java.util.*;
import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.hint.Hint;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.constants.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;


/**
 * @author padfoothelix
 */
public final class TheFourthDoctor extends CardImpl {

    public TheFourthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));
	
	// Would You Like A...? -- Once each turn, you may play a historic land or cast a historic spell from the top of your library. When you do, create a Food token.
        this.addAbility(
                new SimpleStaticAbility(
                        new TheFourthDoctorPlayFromTopEffect())
                        .setIdentifier(MageIdentifier.TheFourthDoctorWatcher)
                        .addHint(TheFourthDoctorWatcher.getHint())
                        .withFlavorWord("Would You Like A…?"),
                new TheFourthDoctorWatcher()
        );
    }

    private TheFourthDoctor(final TheFourthDoctor card) {
        super(card);
    }

    @Override
    public TheFourthDoctor copy() {
        return new TheFourthDoctor(this);
    }
}

// Adapted from PlayFromTopOfLibraryEffect and Johann, Apprentice Sorcerer.
class TheFourthDoctorPlayFromTopEffect extends AsThoughEffectImpl {

    TheFourthDoctorPlayFromTopEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Once each turn, you may play a historic land or cast a historic spell from the top of your library. When you do, create a Food token. "
            + "<i>(Artifacts, legendaries, and Sagas are historic.)</i>";
    }

    private TheFourthDoctorPlayFromTopEffect(final TheFourthDoctorPlayFromTopEffect effect) {
        super(effect);
    }

    @Override
    public TheFourthDoctorPlayFromTopEffect copy() {
        return new TheFourthDoctorPlayFromTopEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {

        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null) {
            return false;
        }

        // to let the player cast a spell
        if (affectedAbility instanceof SpellAbility) {
            SpellAbility spellAbility = (SpellAbility) affectedAbility;
            if (spellAbility.getManaCosts().isEmpty()
                    || !spellAbility.spellCanBeActivatedNow(playerId, game).contains(MageIdentifier.Default)) {
                return false;
            }
            cardToCheck = spellAbility.getCharacteristics(game);
        }

        Player controller = game.getPlayer(source.getControllerId());
        TheFourthDoctorWatcher watcher = game.getState().getWatcher(TheFourthDoctorWatcher.class);
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Player cardOwner = game.getPlayer(cardToCheck.getOwnerId());

        if (controller == null 
                || cardOwner == null
                || sourcePermanent == null 
                || watcher == null) {
            return false;
        }

        // only applies to the controller of the ability.
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }
        // has the ability already been used this turn by the player?
        if (watcher.isAbilityUsed(controller.getId(), new MageObjectReference(sourcePermanent, game))) {
            return false;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        // is the card attempted to be played the top card of the library?
        if (topCard == null || !topCard.getId().equals(cardToCheck.getMainCard().getId())) {
            return false;
        }
        
        // check for historic land or spell and create food token.
        if (cardToCheck.isHistoric(game)) {
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new CreateTokenEffect(new FoodToken()), false);
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return cardToCheck.isHistoric(game);
    }
}
   
// Adapted from OnceEachTurnCastWatcher
class TheFourthDoctorWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> usedFrom = new HashMap<>();

    TheFourthDoctorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if ((event.getType() == GameEvent.EventType.SPELL_CAST
                ||event.getType() == GameEvent.EventType.LAND_PLAYED)
                && event.getPlayerId()!= null
                && event.hasApprovingIdentifier(MageIdentifier.TheFourthDoctorWatcher)) {
            if (event.getType() == GameEvent.EventType.SPELL_CAST) {
                Spell spell = (Spell) game.getObject(event.getTargetId());
                if (spell != null && spell.isHistoric(game)) {
                    usedFrom.computeIfAbsent(event.getPlayerId(), k -> new HashSet<>())
                    .add(event.getApprovingObject().getApprovingMageObjectReference());
                }
            }
            else {
                Card landCard = game.getCard(event.getTargetId());
                if (landCard.isHistoric(game)) {
                    usedFrom.computeIfAbsent(event.getPlayerId(), k -> new HashSet<>())
                    .add(event.getApprovingObject().getApprovingMageObjectReference());
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public boolean isAbilityUsed(UUID playerId, MageObjectReference mor) {
        return usedFrom.getOrDefault(playerId, Collections.emptySet()).contains(mor);
    }

    public static Hint getHint() {
        return TheFourthDoctorHint.instance;
    }

}

enum TheFourthDoctorHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        TheFourthDoctorWatcher watcher = game.getState().getWatcher(TheFourthDoctorWatcher.class);
        if (watcher != null) {
            boolean used = watcher.isAbilityUsed(ability.getControllerId(), new MageObjectReference(ability.getSourceId(), game));
            if (used) {
                Player player = game.getPlayer(ability.getControllerId());
                if (player != null) {
                    return "A historic land has been played or a historic spell has been cast by " + player.getLogName() + " with The Fourth Doctor this turn.";
                }
            }
        }
        return "";
    }

    @Override
    public TheFourthDoctorHint copy() {
        return this;
    }
}

