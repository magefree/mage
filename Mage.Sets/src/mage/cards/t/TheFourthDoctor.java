package mage.cards.t;

import java.util.*;
import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.hint.Hint;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.constants.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
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
	
        // adapted from Serra Paragon
	// Would You Like A...? -- Once each turn, you may play a historic land or cast a historic spell from the top of your library. When you do, create a Food token.
        this.addAbility(
                new SimpleStaticAbility(
                        new TheFourthDoctorPlayFromTopEffect())
                        .setIdentifier(MageIdentifier.TheFourthDoctorWatcher)
                        .addHint(TheFourthDoctorWatcher.getHint())
                        .withFlavorWord("Would You Like A…?"),
                new TheFourthDoctorWatcher()
        );
        this.addAbility(new TheFourthDoctorTriggeredAbility());
    }

    private TheFourthDoctor(final TheFourthDoctor card) {
        super(card);
    }

    @Override
    public TheFourthDoctor copy() {
        return new TheFourthDoctor(this);
    }
}

// adapted from PlayFromTopOfLibraryEffect
class TheFourthDoctorPlayFromTopEffect extends AsThoughEffectImpl {

    TheFourthDoctorPlayFromTopEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Once each turn, you may play a historic land or cast a historic spell from the top of your library. " +
            "When you do, create a Food token.";
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
        
        // only applies for ability's controller 
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }
        
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        TheFourthDoctorWatcher watcher = game.getState().getWatcher(TheFourthDoctorWatcher.class);
        Card cardToCheck = game.getCard(objectId);
        // null checks
        if (controller == null 
                || sourcePermanent == null 
                || watcher == null
                || cardToCheck == null) {
            return false;
        }
        
        if (affectedAbility instanceof SpellAbility) {
            SpellAbility spellAbility = (SpellAbility) affectedAbility;
            if (spellAbility.getManaCosts().isEmpty()
                    || !spellAbility.spellCanBeActivatedNow(playerId, game).contains(MageIdentifier.Default)) {
                return false;
            }
            cardToCheck = spellAbility.getCharacteristics(game);
        }

        Player cardOwner = game.getPlayer(cardToCheck.getOwnerId());
        if (cardOwner == null){
            return false;
        }

        // works only once each turn
        if (watcher.isAbilityUsed(controller.getId(), new MageObjectReference(sourcePermanent, game))) {
            return false;
        }

        // card being played must be top card of library
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard == null || !topCard.getId().equals(cardToCheck.getMainCard().getId())) {
            return false;
        }
        
        // checks that the land/spell is historic
        return cardToCheck.isHistoric(game);
    }
}

class TheFourthDoctorTriggeredAbility extends TriggeredAbilityImpl {

    TheFourthDoctorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new FoodToken()));
        this.setRuleVisible(false);
    }

    private TheFourthDoctorTriggeredAbility(final TheFourthDoctorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheFourthDoctorTriggeredAbility copy() {
        return new TheFourthDoctorTriggeredAbility(this); 
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
            || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // returns true if a card was played via this card's ability 
        // (i.e. same identifier and same approving object)
        if (event.hasApprovingIdentifier(MageIdentifier.TheFourthDoctorWatcher)) {
            return event
                    .getApprovingObject()
                    .getApprovingAbility()
                    .getSourceId()
                    .equals(this.getSourceId());
        }
        return false;
    }
}

// adapted from OnceEachTurnCastWatcher
class TheFourthDoctorWatcher extends Watcher {

    // we store a map of playerIds linked to a set of used approving objects.
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
            usedFrom.computeIfAbsent(event.getPlayerId(), k -> new HashSet<>())
                    .add(event.getApprovingObject().getApprovingMageObjectReference());

        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    boolean isAbilityUsed(UUID playerId, MageObjectReference mor) {
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

