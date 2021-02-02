
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author Quercitron
 */
public final class AshenPowder extends CardImpl {

    public AshenPowder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");


        // Put target creature card from an opponent's graveyard onto the battlefield under your control.
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(new FilterCreatureCard("creature card from an opponent's graveyard")));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
    }

    private AshenPowder(final AshenPowder card) {
        super(card);
    }

    @Override
    public AshenPowder copy() {
        return new AshenPowder(this);
    }
}
