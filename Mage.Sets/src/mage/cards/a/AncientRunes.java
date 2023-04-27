package mage.cards.a;

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

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AncientRunes extends CardImpl {

    public AncientRunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of each player's upkeep, Ancient Runes deals damage to that player equal to the number of artifacts they control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AncientRunesDamageTargetEffect(), TargetController.ANY, false, true));
    }

    private AncientRunes(final AncientRunes card) {
        super(card);
    }

    @Override
    public AncientRunes copy() {
        return new AncientRunes(this);
    }
}

class AncientRunesDamageTargetEffect extends OneShotEffect {

    public AncientRunesDamageTargetEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to that player equal to the number of artifacts they control";
    }

    public AncientRunesDamageTargetEffect(AncientRunesDamageTargetEffect copy) {
        super(copy);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int damage = game.getBattlefield().getAllActivePermanents(new FilterControlledArtifactPermanent("artifacts"), targetPointer.getFirst(game, source), game).size();
            player.damage(damage, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public AncientRunesDamageTargetEffect copy() {
        return new AncientRunesDamageTargetEffect(this);
    }
}
