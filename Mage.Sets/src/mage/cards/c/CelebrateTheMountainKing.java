package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.keyword.RecruitEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author muz
 */
public final class CelebrateTheMountainKing extends CardImpl {

    public CelebrateTheMountainKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When this enchantment enters, for each opponent, exile up to one target nonland permanent that player controls until this enchantment leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect()
            .setTargetPointer(new EachTargetPointer())
            .setText("for each opponent, exile up to one target nonland permanent that player controls until {this} leaves the battlefield")
        );
        ability.addTarget(new TargetNonlandPermanent(0,1));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);

        // When this enchantment enters, recruit.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RecruitEffect()));
    }

    private CelebrateTheMountainKing(final CelebrateTheMountainKing card) {
        super(card);
    }

    @Override
    public CelebrateTheMountainKing copy() {
        return new CelebrateTheMountainKing(this);
    }
}
