package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.DinosaurVanillaToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RoarOfTheFifthPeople extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("Dinosaur card");
    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DINOSAUR, "Dinosaurs you control");

    static {
        filterCard.add(SubType.DINOSAUR.getPredicate());
    }

    public RoarOfTheFifthPeople(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.color.setWhite(true);
        this.color.setGreen(true);
        this.color.setRed(true);
        this.subtype.add(SubType.SAGA);
        this.nightCard = true;

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I -- Create two 3/3 green Dinosaur creature tokens.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new DinosaurVanillaToken(), 2));

        // II -- Roar of the Fifth People gains "Creatures you control have '{T}: Add {R}, {G}, or {W}.'"

        // Note: splitting the mana ability in 3 makes it easier on the UI.
        Ability gainedAbility = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new RedManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ).setText("Creatures you control have '{T}: Add {R}"));
        gainedAbility.addEffect(new GainAbilityControlledEffect(
                new GreenManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ).setText(", {G}"));
        gainedAbility.addEffect(new GainAbilityControlledEffect(
                new WhiteManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false
        ).setText(", or {W}.'"));

        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II,
                new GainAbilitySourceEffect(gainedAbility, Duration.WhileOnBattlefield)
                        .setText("{this} gains \"Creatures you control have '{T}: Add {R}, {G}, or {W}.'\"")
        );

        // III -- Search your library for a Dinosaur card, reveal it, put it into your hand, then shuffle.
        TargetCardInLibrary target = new TargetCardInLibrary(filterCard);
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new SearchLibraryPutInHandEffect(target, true));

        // IV -- Dinosaurs you control gain double strike and trample until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new GainAbilityControlledEffect(
                        DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter
                ).setText("Dinosaurs you control gain double strike"),
                new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(), Duration.EndOfTurn, filter
                ).setText("and trample until end of turn")
        );

        this.addAbility(sagaAbility);
    }

    private RoarOfTheFifthPeople(final RoarOfTheFifthPeople card) {
        super(card);
    }

    @Override
    public RoarOfTheFifthPeople copy() {
        return new RoarOfTheFifthPeople(this);
    }
}
