package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class VoraciousFellBeast extends CardImpl {

    public VoraciousFellBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        
        this.subtype.add(SubType.DRAKE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Voracious Fell Beast enters the battlefield, each opponent sacrifices a creature.
        // Create a Food token for each creature sacrificed this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VoraciousFellBeastEffect(), false));
    }

    private VoraciousFellBeast(final VoraciousFellBeast card) {
        super(card);
    }

    @Override
    public VoraciousFellBeast copy() {
        return new VoraciousFellBeast(this);
    }
}


class VoraciousFellBeastEffect extends OneShotEffect {

    VoraciousFellBeastEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each opponent sacrifices a creature. " +
            "Create a Food token for each creature sacrificed this way";
    }

    VoraciousFellBeastEffect(final VoraciousFellBeastEffect effect) {
        super(effect);
    }

    @Override
    public VoraciousFellBeastEffect copy() {
        return new VoraciousFellBeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        List<UUID> perms = new ArrayList<>();
        for (UUID playerId : game.getOpponents(controller.getId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            TargetControlledCreaturePermanent target =
                new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent(), true);
            if (target.canChoose(player.getId(), source, game)) {
                while (!target.isChosen() && player.canRespond()) {
                    player.choose(Outcome.Sacrifice, target, source, game);
                }
                perms.addAll(target.getTargets());
            }
        }

        int sacrificeCount = 0;
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent == null) {
                continue;
            }
            if (permanent.sacrifice(source, game)) {
                sacrificeCount += 1;
            }
        }
        if (sacrificeCount > 0) {
            game.getState().processAction(game);
            new CreateTokenEffect(new FoodToken(), sacrificeCount).apply(game, source);
        }
        return true;
    }
}
