package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrimazBlightOfOreskos extends CardImpl {

    private static final FilterSpell filter
            = new FilterCreatureSpell("a Phyrexian creature or artifact creature spell");

    static {
        filter.add(Predicates.or(
                SubType.PHYREXIAN.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public BrimazBlightOfOreskos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you cast a Phyrexian creature or artifact creature spell, incubate X, where X is that spell's mana value.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BrimazBlightOfOreskosEffect(), filter, false
        ));

        // At the beginning of each end step, if a Phyrexian died under your control this turn, proliferate.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ProliferateEffect(false), TargetController.ANY,
                BrimazBlightOfOreskosCondition.instance, false
        ), new BrimazBlightOfOreskosWatcher());
    }

    private BrimazBlightOfOreskos(final BrimazBlightOfOreskos card) {
        super(card);
    }

    @Override
    public BrimazBlightOfOreskos copy() {
        return new BrimazBlightOfOreskos(this);
    }
}

class BrimazBlightOfOreskosEffect extends OneShotEffect {

    BrimazBlightOfOreskosEffect() {
        super(Outcome.Benefit);
        staticText = "incubate X, where X is that spell's mana value";
    }

    private BrimazBlightOfOreskosEffect(final BrimazBlightOfOreskosEffect effect) {
        super(effect);
    }

    @Override
    public BrimazBlightOfOreskosEffect copy() {
        return new BrimazBlightOfOreskosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        return spell != null && IncubateEffect.doIncubate(spell.getManaValue(), game, source);
    }
}

enum BrimazBlightOfOreskosCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return BrimazBlightOfOreskosWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "a Phyrexian died under your control this turn";
    }
}

class BrimazBlightOfOreskosWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    BrimazBlightOfOreskosWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent() && zEvent.getTarget().hasSubtype(SubType.PHYREXIAN, game)) {
            players.add(zEvent.getTarget().getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(BrimazBlightOfOreskosWatcher.class)
                .players
                .contains(source.getControllerId());
    }
}
