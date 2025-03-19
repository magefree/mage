package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MobilizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReigningVictor extends CardImpl {

    public ReigningVictor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2/R}{2/W}{2/B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mobilize 1
        this.addAbility(new MobilizeAbility(1));

        // When this creature enters, target creature gets +1/+0 and gains indestructible until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(1, 0).setText("target creature gets +1/+0"));
        ability.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()).setText("and gains indestructible until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ReigningVictor(final ReigningVictor card) {
        super(card);
    }

    @Override
    public ReigningVictor copy() {
        return new ReigningVictor(this);
    }
}
