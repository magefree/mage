package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouControlTwoOrMoreGatesCondition;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.GatesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class UbulSarGatekeepers extends CardImpl {

    public UbulSarGatekeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Ubul Sar Gatekeepers enters the battlefield, if you control two or more Gates, target creature an opponent controls gets -2/-2 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-2, -2))
                .withInterveningIf(YouControlTwoOrMoreGatesCondition.instance);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.addHint(GatesYouControlHint.instance));
    }

    private UbulSarGatekeepers(final UbulSarGatekeepers card) {
        super(card);
    }

    @Override
    public UbulSarGatekeepers copy() {
        return new UbulSarGatekeepers(this);
    }

}
