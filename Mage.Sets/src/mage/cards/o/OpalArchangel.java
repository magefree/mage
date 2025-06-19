package mage.cards.o;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
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
public final class OpalArchangel extends CardImpl {

    public OpalArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // When an opponent casts a creature spell, if Opal Archangel is an enchantment, Opal Archangel becomes a 5/5 Angel creature with flying and vigilance.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        5, 5, "5/5 Angel creature with flying and vigilance", SubType.ANGEL
                ).withAbility(FlyingAbility.getInstance()).withAbility(VigilanceAbility.getInstance()),
                null, Duration.WhileOnBattlefield
        ), StaticFilters.FILTER_SPELL_A_CREATURE, false)
                .withInterveningIf(SourceIsEnchantmentCondition.instance)
                .withRuleTextReplacement(true)
                .setTriggerPhrase("When an opponent casts a creature spell, "));
    }

    private OpalArchangel(final OpalArchangel card) {
        super(card);
    }

    @Override
    public OpalArchangel copy() {
        return new OpalArchangel(this);
    }
}
