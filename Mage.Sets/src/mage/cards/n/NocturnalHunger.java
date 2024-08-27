package mage.cards.n;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NocturnalHunger extends CardImpl {

    public NocturnalHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Gift a Food
        this.addAbility(new GiftAbility(this, GiftType.FOOD));

        // Destroy target creature. If the gift wasn't promised, you lose 2 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LoseLifeSourceControllerEffect(2), GiftWasPromisedCondition.FALSE,
                "if the gift wasn't promised, you lose 2 life"
        ));
    }

    private NocturnalHunger(final NocturnalHunger card) {
        super(card);
    }

    @Override
    public NocturnalHunger copy() {
        return new NocturnalHunger(this);
    }
}
