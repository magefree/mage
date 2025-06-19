package mage.cards.v;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VeiledSerpent extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ISLAND, "an Island");

    public VeiledSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When an opponent casts a spell, if Veiled Serpent is an enchantment, Veiled Serpent becomes a 4/4 Serpent creature that can't attack unless defending player controls an Island.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        4, 4, "4/4 Serpent creature with " +
                        "\"This creature can't attack unless defending player controls an Island.\"",
                        SubType.SERPENT
                ).withAbility(new SimpleStaticAbility(new CantAttackUnlessDefenderControllsPermanent(filter))), null, Duration.WhileOnBattlefield
        ), StaticFilters.FILTER_SPELL_A, false)
                .withInterveningIf(SourceIsEnchantmentCondition.instance)
                .withRuleTextReplacement(true)
                .setTriggerPhrase("When an opponent casts a spell, "));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private VeiledSerpent(final VeiledSerpent card) {
        super(card);
    }

    @Override
    public VeiledSerpent copy() {
        return new VeiledSerpent(this);
    }
}
