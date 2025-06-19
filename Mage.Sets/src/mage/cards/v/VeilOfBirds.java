package mage.cards.v;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VeilOfBirds extends CardImpl {

    public VeilOfBirds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // When an opponent casts a spell, if Veil of Birds is an enchantment, Veil of Birds becomes a 1/1 Bird creature with flying.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        1, 1, "1/1 Bird creature with flying", SubType.BIRD
                ).withAbility(FlyingAbility.getInstance()), null, Duration.WhileOnBattlefield
        ), StaticFilters.FILTER_SPELL_A, false)
                .withInterveningIf(SourceIsEnchantmentCondition.instance)
                .withRuleTextReplacement(true)
                .setTriggerPhrase("When an opponent casts a spell, "));
    }

    private VeilOfBirds(final VeilOfBirds card) {
        super(card);
    }

    @Override
    public VeilOfBirds copy() {
        return new VeilOfBirds(this);
    }
}
