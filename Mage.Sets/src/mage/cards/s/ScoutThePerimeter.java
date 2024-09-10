
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class ScoutThePerimeter extends CardImpl {

    public ScoutThePerimeter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Search your library for a land card, put it onto the battlefield tapped, then suffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterLandCard()), true));

        // Put a bounty counter on up to one target creature an opponent controls.
        Effect effect = new AddCountersTargetEffect(CounterType.BOUNTY.createInstance());
        effect.setText("Put a bounty counter on up to one target creature an opponent controls");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent(0, 1));
    }

    private ScoutThePerimeter(final ScoutThePerimeter card) {
        super(card);
    }

    @Override
    public ScoutThePerimeter copy() {
        return new ScoutThePerimeter(this);
    }
}
