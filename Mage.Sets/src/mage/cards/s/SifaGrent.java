package mage.cards.s;


import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.*;

import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author notshauna
 */

public final class SifaGrent extends CardImpl {

    public SifaGrent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SIFA);
        this.setStartingLoyalty(4);

        // Whenever a creature dies, target opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(new LoseLifeTargetEffect(1), false);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // +1: Goad up to two target creatures.
        Ability ability2 = new LoyaltyAbility(new GoadTargetEffect().setText("Goad up to two target creatures"), 1);
        ability2.addTarget(new TargetCreaturePermanent(0,2));
        this.addAbility(ability2);

        // -6: Each opponent exiles the top two cards of their library. Until end of turn, you may play those cards without paying their mana costs.
         this.addAbility(new LoyaltyAbility(new SifaGrentExileEffect(),-6));

        // Sifa Grent can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }
    private SifaGrent(final SifaGrent card){
        super(card);
    }

    @Override
    public SifaGrent copy() {
        return new SifaGrent(this);
    }
}

class SifaGrentExileEffect extends OneShotEffect {

    SifaGrentExileEffect() {
        super(Outcome.Benefit);
        staticText = " Each opponent exiles the top two cards of their library." +
                " Until end of turn, you may play those cards without paying their mana costs.";
    }

    private SifaGrentExileEffect(final SifaGrentExileEffect effect) {
        super(effect);
    }

    @Override
    public SifaGrentExileEffect copy() {
        return new SifaGrentExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards exiled = new CardsImpl();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }
            Cards cards = new CardsImpl();
            for (Card card : player.getLibrary().getTopCards(game, 2)) {
                player.moveCards(card, Zone.EXILED, source, game);
                cards.add(card);
                }
            exiled.addAll(cards);
            }
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(
                Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, true, false
        ).setTargetPointer(new FixedTargets(exiled, game)), source);
        return true;
    }
}