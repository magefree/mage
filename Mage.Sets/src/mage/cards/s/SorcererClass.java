package mage.cards.s;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SorcererClass extends CardImpl {

    public SorcererClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{R}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When Sorcerer Class enters the battlefield, draw two cards, then discard two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawDiscardControllerEffect(2, 2)
        ));

        // {U}{R}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{U}{R}"));

        // Creatures you control have "{T}: Add {U} or {R}. Spend this mana only to cast an instant or sorcery spell or to gain a Class level."
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ConditionalColoredManaAbility(Mana.BlueMana(1), new SorcererClassManaBuilder()),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("creatures you control have \"{T}: Add {U} or {R}."));
        ability.addEffect(new GainAbilityControlledEffect(
                new ConditionalColoredManaAbility(Mana.RedMana(1), new SorcererClassManaBuilder()),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("Spend this mana only to cast an instant or sorcery spell or to gain a Class level.\""));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {3}{U}{R}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{U}{R}"));

        // Whenever you cast an instant or sorcery spell, that spell deals damage to each opponent equal to the number of instant or sorcery spells you've cast this turn.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new SpellCastControllerTriggeredAbility(
                        new SorcererClassEffect(),
                        StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                        false, true
                ), 3
        )), new SorcererClassWatcher());
    }

    private SorcererClass(final SorcererClass card) {
        super(card);
    }

    @Override
    public SorcererClass copy() {
        return new SorcererClass(this);
    }
}

class SorcererClassManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new SorcererClassConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an instant or sorcery spell or to gain a Class level";
    }
}

class SorcererClassConditionalMana extends ConditionalMana {

    public SorcererClassConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast an instant or sorcery spell or to gain a Class level";
        addCondition(new SorcererClassManaCondition());
    }
}

class SorcererClassManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            return object != null && object.isInstantOrSorcery(game);
        }
        return source instanceof ClassLevelAbility;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}

class SorcererClassEffect extends OneShotEffect {

    SorcererClassEffect() {
        super(Outcome.Benefit);
        staticText = "that spell deals damage to each opponent equal " +
                "to the number of instant and sorcery spells you've cast this turn";
    }

    private SorcererClassEffect(final SorcererClassEffect effect) {
        super(effect);
    }

    @Override
    public SorcererClassEffect copy() {
        return new SorcererClassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        int count = SorcererClassWatcher.spellCount(source.getControllerId(), game);
        if (count < 1) {
            return false;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.damage(count, spell.getId(), source, game);
        }
        return true;
    }
}

class SorcererClassWatcher extends Watcher {

    private final Map<UUID, Integer> spellMap = new HashMap<>();

    SorcererClassWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return;
        }
        spellMap.compute(spell.getControllerId(), CardUtil::setOrIncrementValue);
    }

    @Override
    public void reset() {
        spellMap.clear();
        super.reset();
    }

    static int spellCount(UUID playerId, Game game) {
        SorcererClassWatcher watcher = game.getState().getWatcher(SorcererClassWatcher.class);
        return watcher != null ? watcher.spellMap.getOrDefault(playerId, 0) : 0;
    }
}
