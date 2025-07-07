package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author balazskristof
 */
public class TheFifteenthDoctor extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("the first nonartifact spell you cast each turn");

    static {
        filter.add(TheFifteenthDoctorCastSpellPredicate.instance);
    }

    public TheFifteenthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever The Fifteenth Doctor enters or attacks, mill three cards. You may put an artifact card with mana value 2 or 3 from among them into your hand.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new TheFifteenthDoctorEffect(), false));

        // The first nonartifact spell you cast each turn has improvise. (Your artifacts can help cast that spell. Each artifact you tap after you’re done activating mana abilities pays for {1}.)
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new ImproviseAbility(), filter)
                    .setText("The first nonartifact spell you cast each turn has improvise. " +
                            "<i>(Your artifacts can help cast that spell. Each artifact you " +
                            "tap after you’re done activating mana abilities pays for {1}.)")),
                new TheFifteenthDoctorSpellWatcher()
        );
    }

    private TheFifteenthDoctor(final TheFifteenthDoctor card) {
        super(card);
    }

    @Override
    public TheFifteenthDoctor copy() {
        return new TheFifteenthDoctor(this);
    }
}

class TheFifteenthDoctorEffect extends OneShotEffect {

    public static final FilterCard filter = new FilterArtifactCard("an artifact card with mana value 2 or 3");

    static {
        filter.add(Predicates.or(
                new ManaValuePredicate(ComparisonType.EQUAL_TO, 2),
                new ManaValuePredicate(ComparisonType.EQUAL_TO, 3)
        ));
    }

    TheFifteenthDoctorEffect() {
        super(Outcome.Benefit);
        staticText = "mill three cards. You may put an artifact card " +
                "with mana value 2 or 3 from among them into your hand.";
    }

    private TheFifteenthDoctorEffect(final TheFifteenthDoctorEffect effect) { super(effect); }

    @Override
    public TheFifteenthDoctorEffect copy() { return new TheFifteenthDoctorEffect(this); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = controller.millCards(3, source, game);
        String message = "Put " + filter.getMessage() +
                " from among the milled cards into your hand?";
        if (cards.isEmpty()
                || !controller.chooseUse(outcome, message, source, game)) {
            return true;
        }
        TargetCard target = new TargetCard(Zone.GRAVEYARD, filter);
        if (controller.choose(outcome, cards, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                return controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return false;
    }
}

class TheFifteenthDoctorSpellWatcher extends Watcher {
    private final Map<UUID, Integer> nonartifactSpells;

    public TheFifteenthDoctorSpellWatcher() {
        super(WatcherScope.GAME);
        nonartifactSpells = new HashMap<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null && !spell.isArtifact(game)) {
                nonartifactSpells.put(event.getPlayerId(), nonartifactSpellsCastThisTurn(event.getPlayerId()) + 1);
            }
        }
    }

    public int nonartifactSpellsCastThisTurn(UUID playerId) { return nonartifactSpells.getOrDefault(playerId, 0); }

    @Override
    public void reset() {
        super.reset();
        nonartifactSpells.clear();
    }
}

enum TheFifteenthDoctorCastSpellPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        if (input.getObject() != null && !input.getObject().isArtifact(game)) {
            TheFifteenthDoctorSpellWatcher watcher = game.getState().getWatcher(TheFifteenthDoctorSpellWatcher.class);
            return watcher != null && watcher.nonartifactSpellsCastThisTurn(input.getPlayerId()) == 0;
        }
        return false;
    }

    @Override
    public String toString() { return "The first nonartifact spell you cast each turn"; }
}