
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class TreasuredFind extends CardImpl {

    public TreasuredFind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{G}");



        // Return target card from your graveyard to your hand. Exile Treasured Find.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private TreasuredFind(final TreasuredFind card) {
        super(card);
    }

    @Override
    public TreasuredFind copy() {
        return new TreasuredFind(this);
    }
}
