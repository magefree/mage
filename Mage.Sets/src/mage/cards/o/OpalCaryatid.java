package mage.cards.o;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
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
public final class OpalCaryatid extends CardImpl {

    public OpalCaryatid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // When an opponent casts a creature spell, if Opal Caryatid is an enchantment, Opal Caryatid becomes a 2/2 Soldier creature.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        2, 2, "2/2 Soldier creature", SubType.SOLDIER
                ), null, Duration.WhileOnBattlefield
        ), StaticFilters.FILTER_SPELL_A_CREATURE, false)
                .withInterveningIf(SourceIsEnchantmentCondition.instance)
                .withRuleTextReplacement(true)
                .setTriggerPhrase("When an opponent casts a creature spell, "));
    }

    private OpalCaryatid(final OpalCaryatid card) {
        super(card);
    }

    @Override
    public OpalCaryatid copy() {
        return new OpalCaryatid(this);
    }
}
