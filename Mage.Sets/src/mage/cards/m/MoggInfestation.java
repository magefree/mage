package mage.cards.m;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.GoblinToken;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class MoggInfestation extends CardImpl {

    public MoggInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Destroy all creatures target player controls. For each creature that died this way, create two 1/1 red Goblin creature tokens under that player's control.
        getSpellAbility().addTarget(new TargetPlayer());
        getSpellAbility().addEffect(new MoggInfestationEffect());

    }

    private MoggInfestation(final MoggInfestation card) {
        super(card);
    }

    @Override
    public MoggInfestation copy() {
        return new MoggInfestation(this);
    }
}

class MoggInfestationEffect extends OneShotEffect {

    MoggInfestationEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy all creatures target player controls. For each creature that died this way, create two 1/1 red Goblin creature tokens under that player's control";
    }

    private MoggInfestationEffect(final MoggInfestationEffect effect) {
        super(effect);
    }

    @Override
    public MoggInfestationEffect copy() {
        return new MoggInfestationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<UUID> creaturesDied = new HashSet<>();  // note, permanent and token creatures are counted
        if (controller != null
                && getTargetPointer().getFirst(game, source) != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, getTargetPointer().getFirst(game, source), game)) {
                if (permanent.destroy(source, game, false)) {
                    if (game.getState().getZone(permanent.getId()) == Zone.GRAVEYARD
                            || (permanent instanceof PermanentToken
                            && !game.getBattlefield().containsPermanent(permanent.getId()))) { // If a commander is replaced to command zone, the creature does not die
                        creaturesDied.add(permanent.getId());
                    }
                }
            }
            if (creaturesDied.isEmpty()) {
                return true;
            }
            game.processAction();  // Bug #8548
            for (UUID uuid : creaturesDied) {
                if (game.getState().getZone(uuid) == Zone.GRAVEYARD
                        || (game.getLastKnownInformation(uuid, Zone.BATTLEFIELD) instanceof PermanentToken
                        && !game.getBattlefield().containsPermanent(uuid))) {
                    Effect effect = new CreateTokenTargetEffect(new GoblinToken(), 2);
                    effect.setTargetPointer(this.getTargetPointer().copy());
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
