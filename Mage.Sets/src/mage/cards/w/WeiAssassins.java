
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
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
 * @author TheElk801
 */
public final class WeiAssassins extends CardImpl {

    public WeiAssassins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Wei Assassins enters the battlefield, target opponent chooses a creature they control. Destroy it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new WeiAssassinsEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private WeiAssassins(final WeiAssassins card) {
        super(card);
    }

    @Override
    public WeiAssassins copy() {
        return new WeiAssassins(this);
    }
}

class WeiAssassinsEffect extends OneShotEffect {

    WeiAssassinsEffect() {
        super(Outcome.Benefit);
        this.staticText = "target opponent chooses a creature they control. Destroy it.";
    }

    WeiAssassinsEffect(final WeiAssassinsEffect effect) {
        super(effect);
    }

    @Override
    public WeiAssassinsEffect copy() {
        return new WeiAssassinsEffect(this);
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
        if (target.canChoose(player.getId(), source, game)) {
            while (!target.isChosen() && target.canChoose(player.getId(), source, game) && player.canRespond()) {
                player.chooseTarget(Outcome.DestroyPermanent, target, source, game);
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }
}
