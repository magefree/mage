package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.DinosaurVanillaToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HuatliPoetOfUnity extends TransformingDoubleFacedCard {

    private static final FilterCard filterCard = new FilterCard("Dinosaur card");
    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DINOSAUR, "Dinosaurs you control");

    static {
        filterCard.add(SubType.DINOSAUR.getPredicate());
    }

    public HuatliPoetOfUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARRIOR, SubType.BARD}, "{2}{G}",
                "Roar of the Fifth People",
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "RGW"
        );

        // Huatli, Poet of Unity
        this.getLeftHalfCard().setPT(2, 3);

        // When Huatli, Poet of Unity enters the battlefield, search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true), false));

        // {3}{R/W}{R/W}: Exile Huatli, then return her to the battlefield transformed under her owner's control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{3}{R/W}{R/W}")
        );
        this.getLeftHalfCard().addAbility(ability);

        // Roar of the Fifth People
        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard(), SagaChapter.CHAPTER_IV);

        // I -- Create two 3/3 green Dinosaur creature tokens.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I, new CreateTokenEffect(new DinosaurVanillaToken(), 2));

        // II -- {this} gains "Creatures you control have '{T}: Add {R}, {G}, or {W}.'"
        Ability gainedAbility = new mage.abilities.common.SimpleStaticAbility(new GainAbilityControlledEffect(
                new RedManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ).setText("Creatures you control have '{T}: Add {R}"));
        gainedAbility.addEffect(new GainAbilityControlledEffect(
                new GreenManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ).setText(", {G}"));
        gainedAbility.addEffect(new GainAbilityControlledEffect(
                new WhiteManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ).setText(", or {W}.'"));

        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_II,
                new GainAbilitySourceEffect(gainedAbility, Duration.WhileOnBattlefield)
                        .setText("{this} gains \"Creatures you control have '{T}: Add {R}, {G}, or {W}.'\""));

        // III -- Search your library for a Dinosaur card, reveal it, put it into your hand, then shuffle.
        TargetCardInLibrary target2 = new TargetCardInLibrary(filterCard);
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_III, new SearchLibraryPutInHandEffect(target2, true));

        // IV -- Dinosaurs you control gain double strike and trample until end of turn.
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_IV,
                new GainAbilityControlledEffect(
                        DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter
                ).setText("Dinosaurs you control gain double strike"),
                new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(), Duration.EndOfTurn, filter
                ).setText("and trample until end of turn")
        );

        this.getRightHalfCard().addAbility(sagaAbility);
    }

    private HuatliPoetOfUnity(final HuatliPoetOfUnity card) {
        super(card);
    }

    @Override
    public HuatliPoetOfUnity copy() {
        return new HuatliPoetOfUnity(this);
    }
}
