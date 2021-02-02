
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author North
 */
public final class MarrowShards extends CardImpl {

    public MarrowShards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W/P}");


        this.getSpellAbility().addEffect(new DamageAllEffect(1, new FilterAttackingCreature()));
    }

    private MarrowShards(final MarrowShards card) {
        super(card);
    }

    @Override
    public MarrowShards copy() {
        return new MarrowShards(this);
    }
}
