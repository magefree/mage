package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfXerex extends TransformingDoubleFacedCard {

    public InvasionOfXerex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{W}{U}",
                "Vertex Paladin",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ANGEL, SubType.KNIGHT}, "WU"
        );

        // Invasion of Xerex
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Xerex enters the battlefield, return up to one target creature to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.getLeftHalfCard().addAbility(ability);

        // Vertex Paladin
        this.getRightHalfCard().setPT(0, 0);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Vertex Paladin's power and toughness are each equal to the number of creatures you control.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(CreaturesYouControlCount.PLURAL)));
    }

    private InvasionOfXerex(final InvasionOfXerex card) {
        super(card);
    }

    @Override
    public InvasionOfXerex copy() {
        return new InvasionOfXerex(this);
    }
}
