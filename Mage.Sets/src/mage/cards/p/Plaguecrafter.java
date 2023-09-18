package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author themogwi
 */
public final class Plaguecrafter extends CardImpl {

    public Plaguecrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Plaguecrafter enters the battlefield.
        //   Each player sacrifices a creature or planeswalker.
        //   Each player who can't discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PlaguecrafterEffect()));
    }

    private Plaguecrafter(final Plaguecrafter card) {
        super(card);
    }

    @Override
    public Plaguecrafter copy() {
        return new Plaguecrafter(this);
    }
}

class PlaguecrafterEffect extends OneShotEffect {

    PlaguecrafterEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player sacrifices a creature or planeswalker. "
                + "Each player who can't discards a card.";
    }

    private PlaguecrafterEffect(final PlaguecrafterEffect effect) {
        super(effect);
    }

    @Override
    public PlaguecrafterEffect copy() {
        return new PlaguecrafterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        List<UUID> perms = new ArrayList<>();
        List<UUID> cantSac = new ArrayList<>();

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterControlledPermanent filter = new FilterControlledPermanent("creature or planeswalker");
            filter.add(Predicates.or(
                    CardType.CREATURE.getPredicate(),
                    CardType.PLANESWALKER.getPredicate()
            ));
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
            if (target.canChoose(player.getId(), source, game)) {
                while (!target.isChosen() && player.canRespond()) {
                    player.choose(Outcome.Sacrifice, target, source, game);
                }
                perms.addAll(target.getTargets());
            } else {
                cantSac.add(playerId);
            }
        }

        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }

        for (UUID playerId : cantSac) {
            Effect discardEffect = new DiscardTargetEffect(1);
            discardEffect.setTargetPointer(new FixedTarget(playerId, game));
            discardEffect.apply(game, source);
        }
        return true;
    }
}
