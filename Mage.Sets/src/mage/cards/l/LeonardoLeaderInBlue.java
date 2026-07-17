package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SneakCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class LeonardoLeaderInBlue extends CardImpl {

    public LeonardoLeaderInBlue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sneak {3}{W}{W}
        this.addAbility(new SneakAbility(this, "{3}{W}{W}"));

        // When Leonardo enters, if his sneak cost was paid, creatures you control get +2/+0 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new BoostControlledEffect(2, 0, Duration.EndOfTurn)
        ).withInterveningIf(SneakCondition.instance));

        // {1}{W}: Leonardo gains first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn),
            new ManaCostsImpl<>("{1}{W}")
        ));
    }

    private LeonardoLeaderInBlue(final LeonardoLeaderInBlue card) {
        super(card);
    }

    @Override
    public LeonardoLeaderInBlue copy() {
        return new LeonardoLeaderInBlue(this);
    }
}
