
package mage.cards.e;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PhaseOutAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class Equipoise extends CardImpl {

    public Equipoise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // At the beginning of your upkeep, for each land target player controls in excess of the number you control, choose a land they control, then the chosen permanents phase out. Repeat this process for artifacts and creatures.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new EquipoiseEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Equipoise(final Equipoise card) {
        super(card);
    }

    @Override
    public Equipoise copy() {
        return new Equipoise(this);
    }
}

class EquipoiseEffect extends OneShotEffect {

    public EquipoiseEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each land target player controls in excess of the number you control, choose a land they control, then the chosen permanents phase out. Repeat this process for artifacts and creatures";
    }

    public EquipoiseEffect(final EquipoiseEffect effect) {
        super(effect);
    }

    @Override
    public EquipoiseEffect copy() {
        return new EquipoiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            if (!Objects.equals(targetPlayer, controller)) {
                phaseOutCardType(controller, targetPlayer, CardType.LAND, source, game);
                phaseOutCardType(controller, targetPlayer, CardType.ARTIFACT, source, game);
                phaseOutCardType(controller, targetPlayer, CardType.CREATURE, source, game);
            }
            return true;
        }
        return false;
    }

    private void phaseOutCardType(Player controller, Player targetPlayer, CardType cardType, Ability source, Game game) {
        FilterPermanent filter = new FilterControlledPermanent();
        filter.add(cardType.getPredicate());
        int numberController = game.getBattlefield().count(filter, controller.getId(), source, game);
        int numberTargetPlayer = game.getBattlefield().count(filter, targetPlayer.getId(), source, game);
        int excess = numberTargetPlayer - numberController;
        if (excess > 0) {
            FilterPermanent filterChoose = new FilterPermanent(cardType.toString().toLowerCase(Locale.ENGLISH) + (excess > 1 ? "s" : "") + " of target player");
            filterChoose.add(new ControllerIdPredicate(targetPlayer.getId()));
            filterChoose.add(cardType.getPredicate());
            Target target = new TargetPermanent(excess, excess, filterChoose, true);
            controller.chooseTarget(outcome, target, source, game);
            new PhaseOutAllEffect(target.getTargets()).apply(game, source);
        }
    }
}
