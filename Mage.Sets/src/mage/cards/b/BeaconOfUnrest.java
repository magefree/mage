
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Loki
 */
public final class BeaconOfUnrest extends CardImpl {
    private static final FilterCard filter = new FilterCard("artifact or creature card from a graveyard");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }

    public BeaconOfUnrest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");


        // Put target artifact or creature card from a graveyard onto the battlefield under your control. Shuffle Beacon of Unrest into its owner's library.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
        Target target = new TargetCardInGraveyard(filter);
        this.getSpellAbility().addTarget(target);
    }

    private BeaconOfUnrest(final BeaconOfUnrest card) {
        super(card);
    }

    @Override
    public BeaconOfUnrest copy() {
        return new BeaconOfUnrest(this);
    }
}
