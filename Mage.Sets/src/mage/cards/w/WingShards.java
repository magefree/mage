
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class WingShards extends CardImpl {

    public WingShards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");


        // Target player sacrifices an attacking creature.
        this.getSpellAbility().addEffect(new SacrificeEffect(new FilterAttackingCreature(), 1, "Target player"));
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Storm
        this.addAbility(new StormAbility());
    }

    public WingShards(final WingShards card) {
        super(card);
    }

    @Override
    public WingShards copy() {
        return new WingShards(this);
    }
}
