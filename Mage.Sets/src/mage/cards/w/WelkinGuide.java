package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class WelkinGuide extends CardImpl {

    public WelkinGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(
                2, 2, Duration.EndOfTurn
        ).setText("target creature gets +2/+2"), false);
        ability.addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WelkinGuide(final WelkinGuide card) {
        super(card);
    }

    @Override
    public WelkinGuide copy() {
        return new WelkinGuide(this);
    }
}
