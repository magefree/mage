package mage.cards.t;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheSpotsPortal extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.VILLAIN), ComparisonType.EQUAL_TO, 0
    );

    public TheSpotsPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Put target creature on the bottom of its owner's library. You lose 2 life unless you control a Villain.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LoseLifeSourceControllerEffect(2), condition,
                "You lose 2 life unless you control a Villain"
        ));
    }

    private TheSpotsPortal(final TheSpotsPortal card) {
        super(card);
    }

    @Override
    public TheSpotsPortal copy() {
        return new TheSpotsPortal(this);
    }
}
