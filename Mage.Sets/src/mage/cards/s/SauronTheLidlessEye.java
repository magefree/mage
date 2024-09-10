package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SauronTheLidlessEye extends CardImpl {

    public SauronTheLidlessEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Sauron, the Lidless Eye enters the battlefield, gain control of target creature an opponent controls until end of turn. Untap it. It gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn));
        ability.addEffect(new UntapTargetEffect().setText("untap it"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).setText("It gains haste until end of turn"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // {1}{B}{R}: Creatures you control get +2/+0 until end of turn. Each opponent loses 2 life.
        ability = new SimpleActivatedAbility(
                new BoostControlledEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{B}{R}")
        );
        ability.addEffect(new LoseLifeOpponentsEffect(2));
        this.addAbility(ability);
    }

    private SauronTheLidlessEye(final SauronTheLidlessEye card) {
        super(card);
    }

    @Override
    public SauronTheLidlessEye copy() {
        return new SauronTheLidlessEye(this);
    }
}
