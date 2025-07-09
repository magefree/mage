package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class BruseTarlBoorishHerder extends CardImpl {

    public BruseTarlBoorishHerder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Bruse Tarl, Boorish Herder enters the battlefield or attacks, target creature you control gains double strike and lifelink until end of turn.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GainAbilityTargetEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("target creature you control gains double strike"));
        ability.addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("and lifelink until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private BruseTarlBoorishHerder(final BruseTarlBoorishHerder card) {
        super(card);
    }

    @Override
    public BruseTarlBoorishHerder copy() {
        return new BruseTarlBoorishHerder(this);
    }
}
