package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TributeAbility;
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
public final class SirenOfTheFangedCoast extends CardImpl {

    public SirenOfTheFangedCoast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.SIREN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Tribute 3
        this.addAbility(new TributeAbility(3));

        // When Siren of the Fanged Coast enters the battlefield, if tribute wasn't paid, gain control of target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainControlTargetEffect(Duration.EndOfGame, true)
        ).withInterveningIf(TributeNotPaidCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SirenOfTheFangedCoast(final SirenOfTheFangedCoast card) {
        super(card);
    }

    @Override
    public SirenOfTheFangedCoast copy() {
        return new SirenOfTheFangedCoast(this);
    }
}
