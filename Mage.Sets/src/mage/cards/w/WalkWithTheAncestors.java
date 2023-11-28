package mage.cards.w;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class WalkWithTheAncestors extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public WalkWithTheAncestors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Return up to one target permanent card from your graveyard to your hand. Discover 4.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addEffect(new DiscoverEffect(4));

    }

    private WalkWithTheAncestors(final WalkWithTheAncestors card) {
        super(card);
    }

    @Override
    public WalkWithTheAncestors copy() {
        return new WalkWithTheAncestors(this);
    }
}
