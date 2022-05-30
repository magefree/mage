package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public final class TragicArrogance extends CardImpl {

    public TragicArrogance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // For each player, you choose from among the permanents that player controls an artifact, a creature, an enchantment, and a planeswalker. Then each player sacrifices all other nonland permanents they control.
        this.getSpellAbility().addEffect(new TragicArroganceffect());
    }

    private TragicArrogance(final TragicArrogance card) {
        super(card);
    }

    @Override
    public TragicArrogance copy() {
        return new TragicArrogance(this);
    }
}

class TragicArroganceffect extends OneShotEffect {

    public TragicArroganceffect() {
        super(Outcome.Benefit);
        this.staticText = "For each player, you choose from among the permanents that player controls an artifact, a creature, an enchantment, and a planeswalker. Then each player sacrifices all other nonland permanents they control";
    }

    public TragicArroganceffect(final TragicArroganceffect effect) {
        super(effect);
    }

    @Override
    public TragicArroganceffect copy() {
        return new TragicArroganceffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Permanent> chosenPermanents = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterArtifactPermanent filterArtifactPermanent = new FilterArtifactPermanent("an artifact of " + player.getName());
                    filterArtifactPermanent.add(new ControllerIdPredicate(playerId));
                    Target target1 = new TargetArtifactPermanent(1, 1, filterArtifactPermanent, true);

                    FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent("a creature of " + player.getName());
                    filterCreaturePermanent.add(new ControllerIdPredicate(playerId));
                    Target target2 = new TargetPermanent(1, 1, filterCreaturePermanent, true);

                    FilterEnchantmentPermanent filterEnchantmentPermanent = new FilterEnchantmentPermanent("an enchantment of " + player.getName());
                    filterEnchantmentPermanent.add(new ControllerIdPredicate(playerId));
                    Target target3 = new TargetPermanent(1, 1, filterEnchantmentPermanent, true);

                    FilterPlaneswalkerPermanent filterPlaneswalkerPermanent = new FilterPlaneswalkerPermanent("a planeswalker of " + player.getName());
                    filterPlaneswalkerPermanent.add(new ControllerIdPredicate(playerId));
                    Target target4 = new TargetPermanent(1, 1, filterPlaneswalkerPermanent, true);

                    if (target1.canChoose(controller.getId(), source, game)) {
                        controller.chooseTarget(Outcome.Benefit, target1, source, game);
                        Permanent artifact = game.getPermanent(target1.getFirstTarget());
                        if (artifact != null) {
                            chosenPermanents.add(artifact);
                        }
                        target1.clearChosen();
                    }

                    if (target2.canChoose(controller.getId(), source, game)) {
                        controller.chooseTarget(Outcome.Benefit, target2, source, game);
                        Permanent creature = game.getPermanent(target2.getFirstTarget());
                        if (creature != null) {
                            chosenPermanents.add(creature);
                        }
                        target2.clearChosen();
                    }

                    if (target3.canChoose(controller.getId(), source, game)) {
                        controller.chooseTarget(Outcome.Benefit, target3, source, game);
                        Permanent enchantment = game.getPermanent(target3.getFirstTarget());
                        if (enchantment != null) {
                            chosenPermanents.add(enchantment);
                        }
                        target3.clearChosen();
                    }

                    if (target4.canChoose(controller.getId(), source, game)) {
                        controller.chooseTarget(Outcome.Benefit, target4, source, game);
                        Permanent planeswalker = game.getPermanent(target4.getFirstTarget());
                        if (planeswalker != null) {
                            chosenPermanents.add(planeswalker);
                        }
                        target4.clearChosen();
                    }
                }
            }
            // Then each player sacrifices all other nonland permanents they control
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENTS_NON_LAND, playerId, game)) {
                        if (!chosenPermanents.contains(permanent)) {
                            permanent.sacrifice(source, game);
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }
}
