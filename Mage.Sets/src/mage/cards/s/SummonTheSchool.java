
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.MerfolkWizardToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class SummonTheSchool extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Merfolk you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public SummonTheSchool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.SORCERY},"{3}{W}");
        this.subtype.add(SubType.MERFOLK);
        
        // Create two 1/1 blue Merfolk Wizard creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MerfolkWizardToken(), 2));
        // Tap four untapped Merfolk you control: Return Summon the School from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnToHandSourceEffect(), new TapTargetCost(new TargetControlledPermanent(4, 4, filter, false))));
    }

    private SummonTheSchool(final SummonTheSchool card) {
        super(card);
    }

    @Override
    public SummonTheSchool copy() {
        return new SummonTheSchool(this);
    }
}
