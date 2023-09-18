package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.token.KarnConstructToken;
import mage.target.common.TargetCardInLibrary;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzasSaga extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("artifact card with mana cost {0} or {1}");

    static {
        filter.add(UrzasSagaPredicate.instance);
    }

    public UrzasSaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.LAND}, "");

        this.subtype.add(SubType.URZAS);
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Urza's Saga gains "{T}: Add {C}."
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new GainAbilitySourceEffect(
                        new ColorlessManaAbility(), Duration.Custom
                ).setText("{this} gains \"{T}: Add {C}.\"")
        );

        // II — Urza's Saga gains "{2}, {T}: Create a 0/0 colorless Construct artifact creature token with 'This creature gets +1/+1 for each artifact you control.'"
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new KarnConstructToken()), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new GainAbilitySourceEffect(
                        ability, Duration.Custom
                ).setText("{this} gains \"{2}, {T}: Create a 0/0 colorless Construct artifact creature token with " +
                        "'This creature gets +1/+1 for each artifact you control.'\"")
        );

        // III — Search your library for an artifact card with mana cost {0} or {1}, put it onto the battlefield, then shuffle.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter))
        );
        this.addAbility(sagaAbility);
    }

    private UrzasSaga(final UrzasSaga card) {
        super(card);
    }

    @Override
    public UrzasSaga copy() {
        return new UrzasSaga(this);
    }
}

enum UrzasSagaPredicate implements Predicate<Card> {
    instance;

    private static final List<String> costs = Arrays.asList("{0}", "{1}");

    @Override
    public boolean apply(Card input, Game game) {
        return costs.contains(String.join("", input.getManaCostSymbols()));
    }
}
