
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class MartyrsTomb extends CardImpl {

    public MartyrsTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{B}");


        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new PayLifeCost(2));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MartyrsTomb(final MartyrsTomb card) {
        super(card);
    }

    @Override
    public MartyrsTomb copy() {
        return new MartyrsTomb(this);
    }
}
