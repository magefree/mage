package mage.cards.l;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LifeOfToshiroUmezawa extends TransformingDoubleFacedCard {

    public LifeOfToshiroUmezawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{1}{B}",
                "Memory of Toshiro",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SAMURAI}, "B"
        );

        // Life of Toshiro Umezawa
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I, II — Choose one —
        // • Target creature gets +2/+2 until end of turn.
        // • Target creature gets -1/-1 until end of turn.
        // • You gain 2 life.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, false,
                ability -> {
                    ability.addEffect(new BoostTargetEffect(2, 2));
                    ability.addTarget(new TargetCreaturePermanent());
                    ability.addMode(new Mode(new BoostTargetEffect(-1, -1))
                            .addTarget(new TargetCreaturePermanent()));
                    ability.addMode(new Mode(new GainLifeEffect(2)));
                }
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Memory of Toshiro
        this.getRightHalfCard().setPT(2, 3);

        // {T}, Pay 1 life: Add {B}. Spend this mana only to cast an instant or sorcery spell.
        Ability ability = new ConditionalColoredManaAbility(Mana.BlackMana(1), new InstantOrSorcerySpellManaBuilder());
        ability.addCost(new PayLifeCost(1));
        this.getRightHalfCard().addAbility(ability);
    }

    private LifeOfToshiroUmezawa(final LifeOfToshiroUmezawa card) {
        super(card);
    }

    @Override
    public LifeOfToshiroUmezawa copy() {
        return new LifeOfToshiroUmezawa(this);
    }
}
