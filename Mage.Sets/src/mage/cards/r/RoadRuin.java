package mage.cards.r;

import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoadRuin extends SplitCard {
    public RoadRuin(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY},
                "{2}{G}", "{1}{R}{R}", SpellAbilityType.SPLIT_AFTERMATH
        );

        // Road
        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.getLeftHalfCard().getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));

        // Ruin
        // Aftermath
        this.getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));

        // Ruin deals damage to target creature equal to the number of lands you control.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(LandsYouControlCount.instance)
                .setText("{this} deals damage to target creature equal to the number of lands you control"));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getRightHalfCard().getSpellAbility().addHint(LandsYouControlHint.instance);
    }

    private RoadRuin(final RoadRuin card) {
        super(card);
    }

    @Override
    public RoadRuin copy() {
        return new RoadRuin(this);
    }
}
