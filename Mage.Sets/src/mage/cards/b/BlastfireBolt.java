
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyAllAttachedEquipmentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class BlastfireBolt extends CardImpl {

    public BlastfireBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{R}");


        // Blastfire Bolt deals 5 damage to target creature.  Destroy all Equipment attached to that creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new DestroyAllAttachedEquipmentEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public BlastfireBolt(final BlastfireBolt card) {
        super(card);
    }

    @Override
    public BlastfireBolt copy() {
        return new BlastfireBolt(this);
    }
}
