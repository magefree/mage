package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class KVortClassRaider extends CardImpl {

    public KVortClassRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.SPACECRAFT);

        // During your turn, this Spacecraft and creatures you control have hexproof.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(HexproofAbility.getInstance()),
            MyTurnCondition.instance,
            "during your turn, {this}"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilityControlledEffect(
                HexproofAbility.getInstance(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
            ),
            MyTurnCondition.instance,
            "and creatures you control have hexproof"
        ));
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 4+
        // Flying
        // Menace
        // Haste
        // 5/3
        this.addAbility(new StationLevelAbility(4)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(new MenaceAbility())
            .withLevelAbility(HasteAbility.getInstance())
            .withPT(5, 3));
    }

    private KVortClassRaider(final KVortClassRaider card) {
        super(card);
    }

    @Override
    public KVortClassRaider copy() {
        return new KVortClassRaider(this);
    }
}
