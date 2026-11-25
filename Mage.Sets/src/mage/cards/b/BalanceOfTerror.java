package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 * @author muz
 */
public final class BalanceOfTerror extends CardImpl {

    private static final FilterArtifactOrEnchantmentPermanent filter = new FilterArtifactOrEnchantmentPermanent("other target artifact or enchantment");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("other target permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(AnotherPredicate.instance);
    }

    public BalanceOfTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.subtype.add(SubType.SAGA);

        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Destroy up to one other target artifact or enchantment.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I,
            new DestroyTargetEffect(), new TargetPermanent(0, 1, filter));

        // II, III -- Exile up to one other target permanent you control.
        // Return that card to the battlefield under its owner's control at the beginning of the next end step.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
            new ExileReturnBattlefieldNextEndStepTargetEffect(), new TargetPermanent(0, 1, filter2));
        this.addAbility(sagaAbility);
    }

    private BalanceOfTerror(final BalanceOfTerror card) {
        super(card);
    }

    @Override
    public BalanceOfTerror copy() {
        return new BalanceOfTerror(this);
    }
}
