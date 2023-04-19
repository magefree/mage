package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class FlockchaserPhantom extends CardImpl {

    public FlockchaserPhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Flockchaser Phantom attacks, the next spell you cast this turn has convoke.
        this.addAbility(new AttacksTriggeredAbility(new FlockchaserPhantomEffect()), new FlockchaserPhantomWatcher());
    }

    private FlockchaserPhantom(final FlockchaserPhantom card) {
        super(card);
    }

    @Override
    public FlockchaserPhantom copy() {
        return new FlockchaserPhantom(this);
    }
}

class FlockchaserPhantomEffect extends ContinuousEffectImpl {

    private int spellsCast;
    private final Ability convokeAbility = new ConvokeAbility();

    FlockchaserPhantomEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "the next spell you cast this turn has convoke";
    }

    private FlockchaserPhantomEffect(final FlockchaserPhantomEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        FlockchaserPhantomWatcher watcher = game.getState().getWatcher(FlockchaserPhantomWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getCount(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FlockchaserPhantomWatcher watcher = game.getState().getWatcher(FlockchaserPhantomWatcher.class);
        if (watcher == null) {
            return false;
        }

        //check if a spell was cast before
        if (watcher.getCount(source.getControllerId()) > spellsCast) {
            discard(); // only one use
            return false;
        }

        //check cast for spell and add convoke
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (StackObject stackObject : game.getStack()) {
                if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.isControlledBy(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    game.getState().addOtherAbility(spell.getCard(), convokeAbility);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public FlockchaserPhantomEffect copy() {
        return new FlockchaserPhantomEffect(this);
    }
}

class FlockchaserPhantomWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    FlockchaserPhantomWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null) {
            playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    int getCount(UUID playerId) {
        return playerMap.getOrDefault(playerId, 0);
    }
}
