
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801 & L_J
 */
public final class GoblinWarCry extends CardImpl {

    public GoblinWarCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Target opponent chooses a creature he or she controls. Other creatures he or she controls can't block this turn.
        this.getSpellAbility().addEffect(new GoblinWarCryEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public GoblinWarCry(final GoblinWarCry card) {
        super(card);
    }

    @Override
    public GoblinWarCry copy() {
        return new GoblinWarCry(this);
    }
}

class GoblinWarCryEffect extends OneShotEffect {

    GoblinWarCryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent chooses a creature he or she controls. Other creatures he or she controls can't block this turn.";
    }

    GoblinWarCryEffect(final GoblinWarCryEffect effect) {
        super(effect);
    }

    @Override
    public GoblinWarCryEffect copy() {
        return new GoblinWarCryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");
        filter.add(new ControllerIdPredicate(player.getId()));
        Target target = new TargetPermanent(1, 1, filter, true);
        if (target.canChoose(source.getSourceId(), player.getId(), game)) {
            while (!target.isChosen() && target.canChoose(player.getId(), game) && player.canRespond()) {
                player.chooseTarget(Outcome.DestroyPermanent, target, source, game);
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                game.informPlayers(player.getLogName() + " has chosen " + permanent.getLogName() + " as his only creature able to block this turn");
            }
        }
        game.addEffect(new GoblinWarCryRestrictionEffect(target.getFirstTarget()), source);
        return true;
    }
}

class GoblinWarCryRestrictionEffect extends RestrictionEffect {
    
    protected UUID targetId;

    public GoblinWarCryRestrictionEffect(UUID targetId) {
        super(Duration.EndOfTurn);
        this.targetId = targetId;
    }

    public GoblinWarCryRestrictionEffect(final GoblinWarCryRestrictionEffect effect) {
        super(effect);
        targetId = effect.targetId;
    }

    @Override
    public GoblinWarCryRestrictionEffect copy() {
        return new GoblinWarCryRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.isControlledBy(source.getFirstTarget())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (targetId != null && blocker.getId().equals(targetId)) {
            return true;
        }
        return false;
    }
}
