package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TritonWavebreaker extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public TritonWavebreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {1}{U}
        this.addAbility(new BestowAbility(this, "{1}{U}"));

        // As long as Triton Wavebreaker is a creature, it has prowess.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new ProwessAbility(), Duration.WhileOnBattlefield),
                condition, "as long as {this} is a creature, it has prowess"
        )));

        // Enchanted creature gets +1/+1 and has prowess.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new ProwessAbility(), AttachmentType.AURA
        ).setText("and has prowess"));
        this.addAbility(ability);
    }

    private TritonWavebreaker(final TritonWavebreaker card) {
        super(card);
    }

    @Override
    public TritonWavebreaker copy() {
        return new TritonWavebreaker(this);
    }
}
