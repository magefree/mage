
package mage.cards.v;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author tcontis
 */
public final class VolcanicWind extends CardImpl {
    
    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    static final private String rule = "{this} deals X damage divided as you choose among any number of target creatures, where X is the number of creatures on the battlefield as you cast {this}";

    public VolcanicWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");

        // Volcanic Wind deals X damage divided as you choose among any number of target creatures, where X is the number of creatures as you cast Volcanic Wind.
        PermanentsOnBattlefieldCount creatures = new PermanentsOnBattlefieldCount(filter, null);
        Effect effect = new DamageMultiEffect(creatures);
        effect.setText(rule);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(creatures));

    }

    private VolcanicWind(final VolcanicWind card) {
        super(card);
    }

    @Override
    public VolcanicWind copy() {
        return new VolcanicWind(this);
    }
}
