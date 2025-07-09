package mage.cards.h;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HiddenGuerrillas extends CardImpl {

    public HiddenGuerrillas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent casts an artifact spell, if Hidden Guerrillas is an enchantment, Hidden Guerrillas becomes a 5/3 Soldier creature with trample.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        5, 3, "5/3 Soldier creature with trample", SubType.SOLDIER
                ).withAbility(TrampleAbility.getInstance()), null, Duration.WhileOnBattlefield
        ), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false)
                .withInterveningIf(SourceIsEnchantmentCondition.instance)
                .withRuleTextReplacement(true)
                .setTriggerPhrase("When an opponent casts an artifact spell, "));
    }

    private HiddenGuerrillas(final HiddenGuerrillas card) {
        super(card);
    }

    @Override
    public HiddenGuerrillas copy() {
        return new HiddenGuerrillas(this);
    }
}
