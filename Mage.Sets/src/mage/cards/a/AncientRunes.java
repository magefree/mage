
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class AncientRunes extends CardImpl {

    public AncientRunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // At the beginning of each player's upkeep, Ancient Runes deals damage to that player equal to the number of artifacts he or she controls.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AncientRunesDamageTargetEffect(), TargetController.ANY, false, true));
    }

    public AncientRunes(final AncientRunes card) {
        super(card);
    }

    @Override
    public AncientRunes copy() {
        return new AncientRunes(this);
    }
}

class AncientRunesDamageTargetEffect extends OneShotEffect{
    
    public AncientRunesDamageTargetEffect()
    {
        super(Outcome.Damage);
    }
    
    public AncientRunesDamageTargetEffect(AncientRunesDamageTargetEffect copy)
    {
        super(copy);
    }
        
    @Override
    public String getText(Mode mode) {
        return "{this} deals damage to that player equal to the number of artifacts he or she controls";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int damage = game.getBattlefield().getAllActivePermanents(new FilterControlledArtifactPermanent("artifacts"), targetPointer.getFirst(game, source), game).size();
            player.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public AncientRunesDamageTargetEffect copy() {
        return new AncientRunesDamageTargetEffect(this);
    }
}
