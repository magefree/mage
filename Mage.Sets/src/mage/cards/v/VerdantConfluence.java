
package mage.cards.v;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class VerdantConfluence extends CardImpl {

    public VerdantConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}{G}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);
        
        // - Put two +1/+1 counters on target creature;
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // Return target permanent card from your graveyard to your hand;
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(new FilterPermanentCard()));
        this.getSpellAbility().getModes().addMode(mode);
        
        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        mode = new Mode(new SearchLibraryPutInPlayEffect(target, true));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private VerdantConfluence(final VerdantConfluence card) {
        super(card);
    }

    @Override
    public VerdantConfluence copy() {
        return new VerdantConfluence(this);
    }
}
