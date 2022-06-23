
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author dustinconrad
 */
public final class ShivanMeteor extends CardImpl {

    public ShivanMeteor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");


        // Shivan Meteor deals 13 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(13));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Suspend 2-{1}{R}{R}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl<>("{1}{R}{R}"), this));
    }

    private ShivanMeteor(final ShivanMeteor card) {
        super(card);
    }

    @Override
    public ShivanMeteor copy() {
        return new ShivanMeteor(this);
    }
}
