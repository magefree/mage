package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki
 */
public final class VillageSurvivors extends CardImpl {

    public VillageSurvivors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        this.addAbility(VigilanceAbility.getInstance());
        // Fateful hour - As long as you have 5 or less life, other creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, true),
                FatefulHourCondition.instance,
                "<br><i>Fateful hour</i> &mdash; As long as you have 5 or less life, other creatures you control have vigilance")));
    }

    private VillageSurvivors(final VillageSurvivors card) {
        super(card);
    }

    @Override
    public VillageSurvivors copy() {
        return new VillageSurvivors(this);
    }
}
