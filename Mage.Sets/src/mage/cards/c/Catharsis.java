package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TwoOfManaColorSpentCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KithkinGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Catharsis extends CardImpl {

    public Catharsis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R/W}{R/W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When this creature enters, if {W}{W} was spent to cast it, create two 1/1 green and white Kithkin creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new KithkinGreenWhiteToken(), 2)
        ).withInterveningIf(TwoOfManaColorSpentCondition.WHITE));

        // When this creature enters, if {R}{R} was spent to cast it, creatures you control get +1/+1 and gain haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+1")
        ).withInterveningIf(TwoOfManaColorSpentCondition.RED);
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("and gain haste until end of turn"));
        this.addAbility(ability);

        // Evoke {R/W}{R/W}
        this.addAbility(new EvokeAbility("{R/W}{R/W}"));
    }

    private Catharsis(final Catharsis card) {
        super(card);
    }

    @Override
    public Catharsis copy() {
        return new Catharsis(this);
    }
}
