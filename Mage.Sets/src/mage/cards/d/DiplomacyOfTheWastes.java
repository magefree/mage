
package mage.cards.d;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class DiplomacyOfTheWastes extends CardImpl {

    public DiplomacyOfTheWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card. If you control a Warrior, that player loses 2 life.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(new FilterNonlandCard()));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LoseLifeTargetEffect(2), 
                new PermanentsOnTheBattlefieldCondition(new FilterControlledCreaturePermanent(SubType.WARRIOR, "Warrior")),
                "If you control a Warrior, that player loses 2 life"));

    }

    private DiplomacyOfTheWastes(final DiplomacyOfTheWastes card) {
        super(card);
    }

    @Override
    public DiplomacyOfTheWastes copy() {
        return new DiplomacyOfTheWastes(this);
    }
}
