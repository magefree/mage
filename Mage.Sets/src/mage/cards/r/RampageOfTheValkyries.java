package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AngelVigilanceToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampageOfTheValkyries extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ANGEL, "an Angel you control");

    public RampageOfTheValkyries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{B}");

        // When Rampage of the Valkyries enters the battlefield, create a 4/4 white Angel token with flying and vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AngelVigilanceToken())));

        // Whenever an Angel you control dies, each other player sacrifices a creature.
        this.addAbility(new DiesCreatureTriggeredAbility(new RampageOfTheValkyriesEffect(), false, filter));
    }

    private RampageOfTheValkyries(final RampageOfTheValkyries card) {
        super(card);
    }

    @Override
    public RampageOfTheValkyries copy() {
        return new RampageOfTheValkyries(this);
    }
}

class RampageOfTheValkyriesEffect extends OneShotEffect {

    RampageOfTheValkyriesEffect() {
        super(Outcome.Benefit);
        staticText = "each other player sacrifices a creature";
    }

    private RampageOfTheValkyriesEffect(final RampageOfTheValkyriesEffect effect) {
        super(effect);
    }

    @Override
    public RampageOfTheValkyriesEffect copy() {
        return new RampageOfTheValkyriesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || player.getId().equals(source.getControllerId())) {
                continue;
            }
            TargetPermanent target = new TargetControlledCreaturePermanent();
            target.setNotTarget(true);
            if (!target.canChoose(playerId, source, game)) {
                continue;
            }
            player.choose(outcome, target, source, game);
            perms.add(target.getFirstTarget());
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}
