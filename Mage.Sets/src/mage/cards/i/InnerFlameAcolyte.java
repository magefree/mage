package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class InnerFlameAcolyte extends CardImpl {

    public InnerFlameAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Inner-Flame Acolyte enters the battlefield, target creature gets +2/+0 and gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(
                2, 0, Duration.EndOfTurn
        ).setText("target creature gets +2/+0"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Evoke {R}
        this.addAbility(new EvokeAbility("{R}"));
    }

    private InnerFlameAcolyte(final InnerFlameAcolyte card) {
        super(card);
    }

    @Override
    public InnerFlameAcolyte copy() {
        return new InnerFlameAcolyte(this);
    }
}
