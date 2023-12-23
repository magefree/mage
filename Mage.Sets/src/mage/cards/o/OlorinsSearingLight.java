package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.*;

/**
 *
 * @author notgreat
 */
public final class OlorinsSearingLight extends CardImpl {

    public OlorinsSearingLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{W}");

        // Each opponent exiles a creature with the greatest power among creatures that player controls.
        // Spell mastery -- If there are two or more instant and/or sorcery cards in your graveyard, Olorin's Searing Light deals damage to each opponent equal to the power of the creature they exiled.
        this.getSpellAbility().addEffect(new OlorinsSearingLightEffect());
    }

    private OlorinsSearingLight(final OlorinsSearingLight card) {
        super(card);
    }

    @Override
    public OlorinsSearingLight copy() {
        return new OlorinsSearingLight(this);
    }
}
//See Crackling Doom
class OlorinsSearingLightEffect extends OneShotEffect {

    static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature with the greatest power among creatures you control");
    static {
        filter.add(GreatestPowerControlledPredicate.instance);
    }
    public OlorinsSearingLightEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each opponent exiles a creature with the greatest power among creatures that player controls.<br>"
                +"<i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, {this} deals damage to each opponent equal to the power of the creature they exiled.";
    }

    private OlorinsSearingLightEffect(final OlorinsSearingLightEffect effect) {
        super(effect);
    }

    @Override
    public OlorinsSearingLightEffect copy() {
        return new OlorinsSearingLightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> toExile = new ArrayList<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerId, game)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        Target target = new TargetControlledCreaturePermanent(filter);
                        target.withNotTarget(true);
                        if (opponent.choose(outcome, target, source, game)) {
                            Permanent permanentChosen = game.getPermanent(target.getFirstTarget());
                            if (permanentChosen != null) {
                                toExile.add(permanentChosen);
                            }
                        }
                    }
                }
            }
            List<Map.Entry<Player, Integer>> damageList = new ArrayList<>();
            for (Permanent permanent : toExile) {
                Player opponent = game.getPlayer(permanent.getControllerId());
                if (opponent != null) {
                    damageList.add(new AbstractMap.SimpleImmutableEntry<>(opponent, permanent.getPower().getValue()));
                    opponent.moveCards(permanent, Zone.EXILED, source, game);
                }
            }
            if (SpellMasteryCondition.instance.apply(game, source)){
                game.getState().processAction(game);
                for (Map.Entry<Player, Integer> entry : damageList) {
                    entry.getKey().damage(entry.getValue(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
