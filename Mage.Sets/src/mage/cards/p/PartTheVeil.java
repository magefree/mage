

package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 * @author Loki
 */
public final class PartTheVeil extends CardImpl {

    public PartTheVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");
        this.subtype.add(SubType.ARCANE);
        
        // Return all creatures you control to their owner's hand.
        Effect effect = new ReturnToHandFromBattlefieldAllEffect(new FilterControlledCreaturePermanent());
        effect.setText("Return all creatures you control to their owner's hand");
        this.getSpellAbility().addEffect(effect);
    }

    private PartTheVeil(final PartTheVeil card) {
        super(card);
    }

    @Override
    public PartTheVeil copy() {
        return new PartTheVeil(this);
    }

}
