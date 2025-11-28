package mage.cards.c;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrystalFragments extends TransformingDoubleFacedCard {

    public CrystalFragments(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "{W}",
                "Summon: Alexander",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SAGA, SubType.CONSTRUCT}, "W");

        // Crystal Fragments
        // Equipped creature gets +1/+1.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // {5}{W}{W}: Exile this Equipment, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{5}{W}{W}")
        ));

        // Equip {1}
        this.getLeftHalfCard().addAbility(new EquipAbility(1));

        // Summon: Alexander
        this.getRightHalfCard().setPT(4, 3);

        // Saga setup (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard()).withShowSacText(true);

        // I, II -- Prevent all damage that would be dealt to creatures you control this turn.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                chapterAbility -> chapterAbility.addEffect(new PreventAllDamageToAllEffect(
                        Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED
                )));

        // III -- Tap all creatures your opponents control.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_III,
                chapterAbility -> chapterAbility.addEffect(new TapAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES)));

        this.getRightHalfCard().addAbility(sagaAbility);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());
    }

    private CrystalFragments(final CrystalFragments card) {
        super(card);
    }

    @Override
    public CrystalFragments copy() {
        return new CrystalFragments(this);
    }
}
