
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class TrashForTreasure extends CardImpl {

    private static final FilterControlledPermanent filterPermanent = new FilterControlledPermanent("an artifact");
    private static final FilterCard filterCard = new FilterCard("artifact card from your graveyard");

    static {
        filterPermanent.add(CardType.ARTIFACT.getPredicate());
        filterCard.add(CardType.ARTIFACT.getPredicate());
    }

    public TrashForTreasure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(filterPermanent)));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filterCard));
    }

    private TrashForTreasure(final TrashForTreasure card) {
        super(card);
    }

    @Override
    public TrashForTreasure copy() {
        return new TrashForTreasure(this);
    }
}
