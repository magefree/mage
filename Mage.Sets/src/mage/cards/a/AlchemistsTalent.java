package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class AlchemistsTalent extends CardImpl {

    public AlchemistsTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");
        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When Alchemist’s Talent enters, create two tapped Treasure tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken(), 2, true)));

        // {1}{R}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{R}"));

        // Treasures you control have "{T}, Sacrifice this artifact: Add two mana of any one color."
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost().setText("sacrifice this artifact"));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new GainAbilityControlledEffect(
                        ability, Duration.WhileOnBattlefield,
                        new FilterPermanent(SubType.TREASURE, "Treasures")
                ), 2
        )));

        // {4}{R}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{4}{R}"));

        // Whenever you cast a spell, if mana from a Treasure was spent to cast it, this Class deals damage equal to that spell's mana value to each opponent.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new SpellCastControllerTriggeredAbility(
                        new DamagePlayersEffect(AlchemistsTalentValue.instance, TargetController.OPPONENT)
                                .setText("{this} deals damage equal to that spell's mana value to each opponent"),
                        StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL
                ).withInterveningIf(AlchemistsTalentCondition.instance), 3
        )));
    }

    private AlchemistsTalent(final AlchemistsTalent card) {
        super(card);
    }

    @Override
    public AlchemistsTalent copy() {
        return new AlchemistsTalent(this);
    }
}

enum AlchemistsTalentCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) source.getEffects().get(0).getValue("spellCast");
        return spell != null && ManaPaidSourceWatcher.getTreasurePaid(spell.getSourceId(), game) > 0;
    }

    @Override
    public String toString() {
        return "mana from a Treasure was spent to cast it";
    }
}

enum AlchemistsTalentValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(effect.getValue("spellCast"))
                .filter(Spell.class::isInstance)
                .map(Spell.class::cast)
                .map(Spell::getManaValue)
                .orElse(0);
    }

    @Override
    public AlchemistsTalentValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that spell's mana value";
    }

    @Override
    public String toString() {
        return "1";
    }
}
