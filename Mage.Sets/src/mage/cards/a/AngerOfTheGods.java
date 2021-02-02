
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author Plopman
 */
public final class AngerOfTheGods extends CardImpl {

    public AngerOfTheGods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}{R}");


        // Anger of the Gods deals 3 damage to each creature. 
        this.getSpellAbility().addEffect(new DamageAllEffect(3, new FilterCreaturePermanent()));
        
        //If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn));
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private AngerOfTheGods(final AngerOfTheGods card) {
        super(card);
    }

    @Override
    public AngerOfTheGods copy() {
        return new AngerOfTheGods(this);
    }
}
