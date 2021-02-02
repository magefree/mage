
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public final class GhoulcallersChant extends CardImpl {

    private static final FilterCard filter = new FilterCard("Zombie cards from your graveyard");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public GhoulcallersChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Choose one - Return target creature card from your graveyard to your hand
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        // or return two target Zombie cards from your graveyard to your hand.
        Mode mode = new Mode();
        mode.addEffect(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(2, filter));
        this.getSpellAbility().addMode(mode);
    }

    private GhoulcallersChant(final GhoulcallersChant card) {
        super(card);
    }

    @Override
    public GhoulcallersChant copy() {
        return new GhoulcallersChant(this);
    }
}
