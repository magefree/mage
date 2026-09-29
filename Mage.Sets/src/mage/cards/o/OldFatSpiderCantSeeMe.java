package mage.cards.o;

import mage.abilities.common.SagaAbility;
import mage.abilities.condition.common.SourceRemainsInZoneCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author miesma
 */
public final class OldFatSpiderCantSeeMe extends CardImpl {

    public OldFatSpiderCantSeeMe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I — Target creature you control gains hexproof
        // for as long as this Saga remains on the battlefield.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_I,
                new ConditionalContinuousEffect(
                        new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.Custom),
                        new SourceRemainsInZoneCondition(Zone.BATTLEFIELD),
                        "Target creature you control gains hexproof " +
                                "for as long as {this} remains on the battlefield."
                ), new TargetControlledCreaturePermanent()
        );

        // II — Prevent all damage that would be dealt by up to one target creature
        // for as long as this Saga remains on the battlefield.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_II,
                new ConditionalPreventionEffect(
                        new PreventDamageByTargetEffect(Duration.Custom, false),
                        new SourceRemainsInZoneCondition(Zone.BATTLEFIELD),
                        "Prevent all damage that would be dealt by up to one target creature " +
                                "for as long as {this} remains on the battlefield."
                ), new TargetCreaturePermanent(0, 1)
        );

        // III, IV — Draw a card.
        sagaAbility.addChapterEffect(this,
                SagaChapter.CHAPTER_III,
                SagaChapter.CHAPTER_IV,
                new DrawCardSourceControllerEffect(1));
        this.addAbility(sagaAbility);
    }

    private OldFatSpiderCantSeeMe(final OldFatSpiderCantSeeMe card) {
        super(card);
    }

    @Override
    public OldFatSpiderCantSeeMe copy() {
        return new OldFatSpiderCantSeeMe(this);
    }
}
