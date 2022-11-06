package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Bushwhack extends CardImpl {

    public Bushwhack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Choose one --
        // * Search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));

        // * Target creature you control fights target creature you don't control.
        this.getSpellAbility().addMode(new Mode(new FightTargetsEffect())
                .addTarget(new TargetControlledCreaturePermanent())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL)));
    }

    private Bushwhack(final Bushwhack card) {
        super(card);
    }

    @Override
    public Bushwhack copy() {
        return new Bushwhack(this);
    }
}
