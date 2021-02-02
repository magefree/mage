
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author LevelX2
 */
public final class DevouringLight extends CardImpl {

    public DevouringLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");


        // Convoke
        this.addAbility(new ConvokeAbility());
        
        // Exile target attacking or blocking creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private DevouringLight(final DevouringLight card) {
        super(card);
    }

    @Override
    public DevouringLight copy() {
        return new DevouringLight(this);
    }
}
