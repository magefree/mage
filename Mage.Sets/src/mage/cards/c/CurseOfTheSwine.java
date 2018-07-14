
package mage.cards.c;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CurseOfTheSwineBoarToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CurseOfTheSwine extends CardImpl {

    public CurseOfTheSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Exile X target creatures. For each creature exiled this way, its controller creates a 2/2 green Boar creature token.
        this.getSpellAbility().addEffect(new CurseOfTheSwineEffect());
        // Correct number of targets will be set in adjustTargets
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility && ability.getAbilityType() == AbilityType.SPELL) {
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
        }
    }

    public CurseOfTheSwine(final CurseOfTheSwine card) {
        super(card);
    }

    @Override
    public CurseOfTheSwine copy() {
        return new CurseOfTheSwine(this);
    }
}

class CurseOfTheSwineEffect extends OneShotEffect {

    public CurseOfTheSwineEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile X target creatures. For each creature exiled this way, its controller creates a 2/2 green Boar creature token";
    }

    public CurseOfTheSwineEffect(final CurseOfTheSwineEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfTheSwineEffect copy() {
        return new CurseOfTheSwineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> playersWithTargets = new HashMap<>();
            for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                Permanent creature = game.getPermanent(targetId);
                if (creature != null) {
                    if (controller.moveCards(creature, Zone.EXILED, source, game)) {
                        playersWithTargets.put(creature.getControllerId(), playersWithTargets.getOrDefault(creature.getControllerId(), 0) + 1);
                    }
                }
            }
            CurseOfTheSwineBoarToken swineToken = new CurseOfTheSwineBoarToken();
            for (UUID playerId : playersWithTargets.keySet()) {
                swineToken.putOntoBattlefield(playersWithTargets.get(playerId), game, source.getSourceId(), playerId);
            }
            return true;
        }
        return false;

    }
}
