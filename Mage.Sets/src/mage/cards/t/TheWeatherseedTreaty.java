package mage.cards.t;

import java.util.UUID;

import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class TheWeatherseedTreaty extends CardImpl {

    public TheWeatherseedTreaty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.SAGA);

        // Read ahead
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III, true);

        // I -- Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true)
        );

        // II -- Create a 1/1 green Saproling creature token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, new CreateTokenEffect(new SaprolingToken())
        );

        // III -- Domain -- Target creature you control gets +X/+X and gains trample until end of turn, where X is the number of basic land types among lands you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new Effects(
                        new BoostTargetEffect(DomainValue.REGULAR, DomainValue.REGULAR, Duration.EndOfTurn)
                                .setText("<i>Domain</i> &mdash; Target creature you control gets +X/+X"),
                        new GainAbilityTargetEffect(TrampleAbility.getInstance())
                                .setText("and gains trample until end of turn, where X is the number of basic land types among lands you control")
                ),
                new TargetControlledCreaturePermanent()
        );
        this.addAbility(sagaAbility);
    }

    private TheWeatherseedTreaty(final TheWeatherseedTreaty card) {
        super(card);
    }

    @Override
    public TheWeatherseedTreaty copy() {
        return new TheWeatherseedTreaty(this);
    }
}
