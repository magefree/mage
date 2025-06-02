package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class MortisDogs extends CardImpl {

    public MortisDogs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Mortis Dogs attacks, it gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), false));

        // When Mortis Dogs dies, target player loses life equal to its power.
        Ability ability = new DiesSourceTriggeredAbility(new LoseLifeTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE)
                .setText("target player loses life equal to its power"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private MortisDogs(final MortisDogs card) {
        super(card);
    }

    @Override
    public MortisDogs copy() {
        return new MortisDogs(this);
    }
}
