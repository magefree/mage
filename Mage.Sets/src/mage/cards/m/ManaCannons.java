package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ManaCannons extends CardImpl {

    public ManaCannons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever you cast a multicolored spell, Mana Cannons deals X damage to any target, where X is the number of colors that spell is.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(ManaCannonsSpellValue.instance)
                        .setText("{this} deals X damage to any target, where X is the number of colors that spell is"),
                StaticFilters.FILTER_SPELL_A_MULTICOLORED, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ManaCannons(final ManaCannons card) {
        super(card);
    }

    @Override
    public ManaCannons copy() {
        return new ManaCannons(this);
    }
}

enum ManaCannonsSpellValue implements DynamicValue {
    instance();

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Spell spell = (Spell) effect.getValue("spellCast");
        return spell != null ? spell.getColor(game).getColorCount() : 0;
    }

    @Override
    public ManaCannonsSpellValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
