package mage.cards.s;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.MerfolkWizardToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SummonTheSchool extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.MERFOLK, "untapped Merfolk you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SummonTheSchool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{3}{W}");
        this.subtype.add(SubType.MERFOLK);

        // Create two 1/1 blue Merfolk Wizard creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MerfolkWizardToken(), 2));

        // Tap four untapped Merfolk you control: Return Summon the School from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                new TapTargetCost(new TargetControlledPermanent(4, filter))
        ));
    }

    private SummonTheSchool(final SummonTheSchool card) {
        super(card);
    }

    @Override
    public SummonTheSchool copy() {
        return new SummonTheSchool(this);
    }
}
