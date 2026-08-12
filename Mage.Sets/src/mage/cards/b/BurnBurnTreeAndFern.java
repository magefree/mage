package mage.cards.b;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.Mana;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class BurnBurnTreeAndFern extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifact an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }


    public BurnBurnTreeAndFern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I -- This Saga deals 6 damage to target creature an opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new DamageTargetEffect(6), new TargetOpponentsCreaturePermanent()
        );

        // II -- Destroy target artifact an opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new DestroyTargetEffect(), new TargetPermanent(filter)
        );

        // III, IV -- Add {R}.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_IV,
                new BasicManaEffect(Mana.RedMana(1))
        );

        this.addAbility(sagaAbility);
    }

    private BurnBurnTreeAndFern(final BurnBurnTreeAndFern card) {
        super(card);
    }

    @Override
    public BurnBurnTreeAndFern copy() {
        return new BurnBurnTreeAndFern(this);
    }
}
