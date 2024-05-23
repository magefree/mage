package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class GraspOfFate extends CardImpl {

    public GraspOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // When Grasp of Fate enters the battlefield, for each opponent, exile up to one target nonland permanent that player controls until Grasp of Fate leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, exile up to one target nonland permanent that player controls until {this} leaves the battlefield")
        );
        ability.setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
        ability.addTarget(new TargetNonlandPermanent(0,1));
        this.addAbility(ability);
    }

    private GraspOfFate(final GraspOfFate card) {
        super(card);
    }

    @Override
    public GraspOfFate copy() {
        return new GraspOfFate(this);
    }
}
