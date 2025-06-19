package mage.cards.o;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BecomesEnchantmentSourceEffect;
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
public final class OpalAcrolith extends CardImpl {

    public OpalAcrolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever an opponent casts a creature spell, if Opal Acrolith is an enchantment, Opal Acrolith becomes a 2/4 Soldier creature.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new BecomesCreatureSourceEffect(new CreatureToken(
                        2, 4, "2/4 Soldier creature", SubType.SOLDIER
                ), null, Duration.WhileOnBattlefield), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ).withInterveningIf(SourceIsEnchantmentCondition.instance).withRuleTextReplacement(true));

        // {0}: Opal Acrolith becomes an enchantment.
        this.addAbility(new SimpleActivatedAbility(new BecomesEnchantmentSourceEffect().setText("this permanent becomes an enchantment"), new ManaCostsImpl<>("{0}")));
    }

    private OpalAcrolith(final OpalAcrolith card) {
        super(card);
    }

    @Override
    public OpalAcrolith copy() {
        return new OpalAcrolith(this);
    }
}
