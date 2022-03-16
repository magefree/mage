
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class MarchOfTheDrowned extends CardImpl {

    private static final FilterCard filter = new FilterCard("Pirate cards from your graveyard");

    static {
        filter.add(SubType.PIRATE.getPredicate());
    }

    public MarchOfTheDrowned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Choose one —
        // &amp;bull; Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        // &amp;bull; Return two target Pirate cards from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(2, filter));
        this.getSpellAbility().addMode(mode);
    }

    private MarchOfTheDrowned(final MarchOfTheDrowned card) {
        super(card);
    }

    @Override
    public MarchOfTheDrowned copy() {
        return new MarchOfTheDrowned(this);
    }
}
