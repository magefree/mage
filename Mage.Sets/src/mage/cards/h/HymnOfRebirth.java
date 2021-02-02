
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LoneFox
 */
public final class HymnOfRebirth extends CardImpl {

    public HymnOfRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{W}");

        // Put target creature card from a graveyard onto the battlefield under your control.
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
    }

    private HymnOfRebirth(final HymnOfRebirth card) {
        super(card);
    }

    @Override
    public HymnOfRebirth copy() {
        return new HymnOfRebirth(this);
    }
}
