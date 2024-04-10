package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnigmaThief extends CardImpl {

    public EnigmaThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Prowl {3}{U}
        this.addAbility(new ProwlAbility("{3}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Enigma Thief enters the battlefield, for each opponent, return up to one target nonland permanent that player controls to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, return up to one target nonland permanent that player controls to its owner's hand"));
        ability.setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
        ability.addTarget(new TargetNonlandPermanent(0,1));
        this.addAbility(ability);
    }

    private EnigmaThief(final EnigmaThief card) {
        super(card);
    }

    @Override
    public EnigmaThief copy() {
        return new EnigmaThief(this);
    }
}

