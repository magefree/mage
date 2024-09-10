
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.Effect;
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
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
        Effect effect = new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("target creature you control gains double strike");
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(effect);
        effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and lifelink until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("target creature you control")));

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
