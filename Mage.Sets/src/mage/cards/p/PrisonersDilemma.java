package mage.cards.p;

import java.util.*;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Skiwkr
 */
public final class PrisonersDilemma extends CardImpl {

    public PrisonersDilemma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        

        // Each opponent secretly chooses silence or snitch, then the choices are revealed. If each opponent chose silence, Prisoner's Dilemma deals 4 damage to each of them. If each opponent chose snitch, Prisoner's Dilemma deals 8 damage to each of them. Otherwise, Prisoner's Dilemma deals 12 damage to each opponent who chose silence.
        this.getSpellAbility().addEffect(new PrisonersDilemmaEffect());
        // Flashback {5}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{R}{R}")));

    }

    private PrisonersDilemma(final PrisonersDilemma card) {
        super(card);
    }

    @Override
    public PrisonersDilemma copy() {
        return new PrisonersDilemma(this);
    }
}

class PrisonersDilemmaEffect extends OneShotEffect {

    PrisonersDilemmaEffect() {
        super(Outcome.Benefit);
        staticText = "Each opponent secretly chooses silence or snitch, then the choices are revealed. " +
                "If each opponent chose silence, {this} deals 4 damage to each of them. " +
                "If each opponent chose snitch, {this} deals 8 damage to each of them. "+
                "Otherwise, {this} deals 12 damage to each opponent who chose silence.";
    }

    private PrisonersDilemmaEffect(final PrisonersDilemmaEffect effect) {
        super(effect);
    }

    @Override
    public PrisonersDilemmaEffect copy() {
        return new PrisonersDilemmaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Player> silence = new ArrayList<>();
        List<Player> snitch = new ArrayList<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            boolean choseSilence = opponent.chooseUse(Outcome.Vote, "Choose silence or snitch", null, "Silence", "Snitch", source, game);
            if (choseSilence) {
                silence.add(opponent);
            } else {
                snitch.add(opponent);
            }
        }

        for (Player player : snitch) {
            game.informPlayers(player.getName() + " chose snitch");
        }

        for (Player player : silence) {
            game.informPlayers(player.getName() + " chose silence");
        }

        if (snitch.isEmpty()) {
            for (Player player : silence) {
                player.damage(4,source.getSourceId(),source,game);
            }
        } else if (silence.isEmpty()) {
            for(Player player:snitch){
                player.damage(8,source.getSourceId(),source,game);
            }
        } else {
            for(Player player : silence) {
                player.damage(12, source.getSourceId(), source, game);
            }
        }
        return true;
    }
}