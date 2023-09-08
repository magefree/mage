package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TemptWithReflections extends CardImpl {

    public TemptWithReflections(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Tempting offer - Choose target creature you control. Create a token that's a copy of that creature. Each opponent may create a token that's a copy of that creature. For each opponent who does, create a token that's a copy of that creature.
        this.getSpellAbility().addEffect(new TemptWithReflectionsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private TemptWithReflections(final TemptWithReflections card) {
        super(card);
    }

    @Override
    public TemptWithReflections copy() {
        return new TemptWithReflections(this);
    }
}

class TemptWithReflectionsEffect extends OneShotEffect {

    public TemptWithReflectionsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "<i>Tempting offer</i> &mdash; Choose target creature you control. Create a token that's a copy of that creature. Each opponent may create a token that's a copy of that creature. For each opponent who does, create a token that's a copy of that creature";
    }

    private TemptWithReflectionsEffect(final TemptWithReflectionsEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithReflectionsEffect copy() {
        return new TemptWithReflectionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            Effect effect = new CreateTokenCopyTargetEffect();
            effect.setTargetPointer(getTargetPointer());
            effect.apply(game, source);

            Set<UUID> playersSaidYes = new HashSet<>();
            PlayerList playerList = game.getPlayerList().copy();
            playerList.setCurrent(game.getActivePlayerId());
            Player player = game.getPlayer(game.getActivePlayerId());
            do {
                if (game.getOpponents(source.getControllerId()).contains(player.getId())) {
                    String decision;
                    if (player.chooseUse(outcome, "Create a copy of target creature for you?", source, game)) {
                        playersSaidYes.add(player.getId());
                        decision = " chooses to copy ";
                    } else {
                        decision = " won't copy ";
                    }
                    game.informPlayers((player.getLogName() + decision + permanent.getName()));
                }
                player = playerList.getNext(game, false);
            } while (player != null && !player.getId().equals(game.getActivePlayerId()));

            for (UUID playerId : playersSaidYes) {
                effect = new CreateTokenCopyTargetEffect(playerId);
                effect.setTargetPointer(getTargetPointer());
                effect.apply(game, source);

                // create a token for the source controller as well
                effect = new CreateTokenCopyTargetEffect();
                effect.setTargetPointer(getTargetPointer());
                effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
