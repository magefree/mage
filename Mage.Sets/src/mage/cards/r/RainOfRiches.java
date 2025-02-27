package mage.cards.r;

import mage.MageObjectReference;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Alex-Vasile, Susucr
 */
public class RainOfRiches extends CardImpl {


    private static final FilterNonlandCard filter =
            new FilterNonlandCard("The first spell you cast each turn that mana from a Treasure was spent to cast");

    static {
        filter.add(RainOfRichesPredicate.instance);
    }

    public RainOfRiches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // When Rain of Riches enters the battlefield, create two Treasure tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken(), 2)));

        // The first spell you cast each turn that mana from a Treasure was spent to cast has cascade.
        //      (When you cast the spell, exile cards from the top of your library until you exile a nonland card that costs less.
        //       You may cast it without paying its mana cost.
        //       Put the exiled cards on the bottom of your library in a random order.)
        this.addAbility(
                new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new CascadeAbility(false), filter)),
                new RainOfRichesWatcher()
        );
    }

    private RainOfRiches(final RainOfRiches card) {
        super(card);
    }

    @Override
    public RainOfRiches copy() {
        return new RainOfRiches(this);
    }
}

enum RainOfRichesPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        if (sourcePermanent == null || !sourcePermanent.getControllerId().equals(input.getPlayerId())) {
            return false;
        }
        RainOfRichesWatcher watcher = game.getState().getWatcher(RainOfRichesWatcher.class);
        Card card = input.getObject();
        return watcher != null
                && card instanceof StackObject
                && watcher.checkSpell((Spell) card, game);
    }
}

class RainOfRichesWatcher extends Watcher {

    private final Map<UUID, MageObjectReference> playerMap = new HashMap<>();

    RainOfRichesWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell == null) {
            return;
        }
        int manaPaid = ManaPaidSourceWatcher.getTreasurePaid(spell.getId(), game);
        if (manaPaid < 1) {
            return;
        }

        playerMap.computeIfAbsent(event.getPlayerId(), x -> new MageObjectReference(spell.getMainCard(), game));
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    boolean checkSpell(StackObject stackObject, Game game) {
        if (stackObject.isCopy()
                || !(stackObject instanceof Spell)) {
            return false;
        }
        if (playerMap.containsKey(stackObject.getControllerId())) {
            return playerMap.get(stackObject.getControllerId()).refersTo(((Spell) stackObject).getMainCard(), game);
        } else {
            return ManaPaidSourceWatcher.getTreasurePaid(stackObject.getId(), game) >= 1;
        }
    }
}
