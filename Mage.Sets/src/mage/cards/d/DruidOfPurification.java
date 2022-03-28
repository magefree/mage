package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DruidOfPurification extends CardImpl {

    public DruidOfPurification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Druid of Purification enters the battlefield, starting with you, each player may choose an artifact or enchantment you don't control. Destroy each permanent chosen this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DruidOfPurificationEffect()));
    }

    private DruidOfPurification(final DruidOfPurification card) {
        super(card);
    }

    @Override
    public DruidOfPurification copy() {
        return new DruidOfPurification(this);
    }
}

class DruidOfPurificationEffect extends OneShotEffect {

    DruidOfPurificationEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player may choose an artifact " +
                "or enchantment you don't control. Destroy each permanent chosen this way";
    }

    private DruidOfPurificationEffect(final DruidOfPurificationEffect effect) {
        super(effect);
    }

    @Override
    public DruidOfPurificationEffect copy() {
        return new DruidOfPurificationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent(
                "artifact or enchantment not controlled by " + controller.getName()
        );
        filter.add(Predicates.not(new ControllerIdPredicate(controller.getId())));
        if (game.getBattlefield().count(filter, source.getControllerId(), source, game) < 1) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 1, filter);
        target.setNotTarget(true);
        Set<Permanent> permanents = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            target.clearChosen();
            player.choose(Outcome.DestroyPermanent, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                game.informPlayers(player.getLogName() + " has not chosen a permanent");
                continue;
            }
            game.informPlayers(player.getLogName() + " has chosen " + permanent.getLogName());
            permanents.add(permanent);
        }
        for (Permanent permanent : permanents) {
            permanent.destroy(source, game, false);
        }
        return true;
    }
}
