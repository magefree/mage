package mage.cards.v;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
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
public final class VeiledApparition extends CardImpl {

    public VeiledApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // When an opponent casts a spell, if Veiled Apparition is an enchantment, Veiled Apparition becomes a 3/3 Illusion creature with flying and "At the beginning of your upkeep, sacrifice Veiled Apparition unless you pay {1}{U}."
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        3, 3, "3/3 Illusion creature with flying and \"At the beginning " +
                        "of your upkeep, sacrifice this creature unless you pay {1}{U}.\"", SubType.ILLUSION
                ).withAbility(FlyingAbility.getInstance()).withAbility(new BeginningOfUpkeepTriggeredAbility(
                        new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{1}{U}"))
                )), null, Duration.WhileOnBattlefield
        ), StaticFilters.FILTER_SPELL_A, false)
                .withInterveningIf(SourceIsEnchantmentCondition.instance)
                .withRuleTextReplacement(true)
                .setTriggerPhrase("When an opponent casts a spell, "));
    }

    private VeiledApparition(final VeiledApparition card) {
        super(card);
    }

    @Override
    public VeiledApparition copy() {
        return new VeiledApparition(this);
    }
}
