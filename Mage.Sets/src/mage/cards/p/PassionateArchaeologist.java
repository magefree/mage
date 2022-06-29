package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetOpponent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PassionateArchaeologist extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from exile");

    static {
        filter.add(new CastFromZonePredicate(Zone.EXILED));
    }

    public PassionateArchaeologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever you cast a spell from exile, this creature deals damage equal to that spell's mana value to target opponent."
        Ability ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(
                PassionateArchaeologistValue.instance, "this creature"
        ), filter, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private PassionateArchaeologist(final PassionateArchaeologist card) {
        super(card);
    }

    @Override
    public PassionateArchaeologist copy() {
        return new PassionateArchaeologist(this);
    }
}

enum PassionateArchaeologistValue implements DynamicValue {
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
    public PassionateArchaeologistValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that spell's mana value";
    }

    @Override
    public String toString() {
        return "";
    }
}
