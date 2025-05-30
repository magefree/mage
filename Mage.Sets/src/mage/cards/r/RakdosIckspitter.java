
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class RakdosIckspitter extends CardImpl {

    public RakdosIckspitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{R}");
        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Rakdos Ickspitter deals 1 damage to target creature and that creature's controller loses 1 life.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        Effect effect = new LoseLifeTargetControllerEffect(1);
        effect.setText("and that creature's controller loses 1 life");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RakdosIckspitter(final RakdosIckspitter card) {
        super(card);
    }

    @Override
    public RakdosIckspitter copy() {
        return new RakdosIckspitter(this);
    }
}
