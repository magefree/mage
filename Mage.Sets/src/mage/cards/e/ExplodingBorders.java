package mage.cards.e;

import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ExplodingBorders extends CardImpl {

    public ExplodingBorders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{G}");

        // Domain - Search your library for a basic land card, put that card onto the battlefield tapped, then shuffle your library. Exploding Borders deals X damage to target player, where X is the number of basic land types among lands you control.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true));
        this.getSpellAbility().addEffect(new DamageTargetEffect(new DomainValue()));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addHint(DomainHint.instance);
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
    }

    private ExplodingBorders(final ExplodingBorders card) {
        super(card);
    }

    @Override
    public ExplodingBorders copy() {
        return new ExplodingBorders(this);
    }
}
