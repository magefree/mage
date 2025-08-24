package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UthrosResearchCraft extends CardImpl {

    public UthrosResearchCraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 3+
        // Whenever you cast an artifact spell, draw a card. Put a charge counter on this Spacecraft.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_SPELL_AN_ARTIFACT, false
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()));
        this.addAbility(new StationLevelAbility(3).withLevelAbility(ability));

        // STATION 12+
        // Flying
        // This Spacecraft gets +1/+0 for each artifact you control.
        // 0/8
        this.addAbility(new StationLevelAbility(12)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(new SimpleStaticAbility(new BoostSourceEffect(
                        ArtifactYouControlCount.instance, StaticValue.get(0), Duration.WhileOnBattlefield
                )))
                .withPT(0, 8));
    }

    private UthrosResearchCraft(final UthrosResearchCraft card) {
        super(card);
    }

    @Override
    public UthrosResearchCraft copy() {
        return new UthrosResearchCraft(this);
    }
}
