package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class CraterclawColossus extends CardImpl {

    public CraterclawColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Craterclaw Colossus enters, creatures you control gain trample and get +X/+0 until end of turn, where X is the number of artifacts you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("creatures you control gain trample"));
        ability.addEffect(new BoostControlledEffect(
                ArtifactYouControlCount.instance, StaticValue.get(0),
                Duration.EndOfTurn
        ).setText("and get +X/+0 until end of turn, where X is the number of artifacts you control"));
        ability.addHint(ArtifactYouControlHint.instance);
        this.addAbility(ability);
    }

    private CraterclawColossus(final CraterclawColossus card) {
        super(card);
    }

    @Override
    public CraterclawColossus copy() {
        return new CraterclawColossus(this);
    }
}
