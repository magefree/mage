
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SpiteOfMogis extends CardImpl {

    public SpiteOfMogis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");


        // Spite of Mogis deals damage to target creature equal to the number of instant and sorcery cards in your graveyard. Scry 1.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new CardsInControllerGraveyardCount(new FilterInstantOrSorceryCard("instant and sorcery cards"))));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private SpiteOfMogis(final SpiteOfMogis card) {
        super(card);
    }

    @Override
    public SpiteOfMogis copy() {
        return new SpiteOfMogis(this);
    }
}
