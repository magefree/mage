package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.VampireKnightToken;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SorinGrimNemesis extends CardImpl {
    
    public SorinGrimNemesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{W}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SORIN);
        
        this.setStartingLoyalty(6);

        // +1: Reveal the top card of your library and put that card into your hand. Each opponent loses life equal to its converted mana cost.
        this.addAbility(new LoyaltyAbility(new SorinGrimNemesisRevealEffect(), 1));

        // -X: Sorin, Grim Nemesis deals X damage to target creature or planeswalker and you gain X life.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(GetXLoyaltyValue.instance));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        ability.addEffect(new GainLifeEffect(GetXLoyaltyValue.instance).concatBy("and"));
        this.addAbility(ability);

        // -9: Create a number of 1/1 black Vampire Knight creature tokens with lifelink equal to the highest life total among all players.
        this.addAbility(new LoyaltyAbility(new SorinTokenEffect(), -9));
    }
    
    private SorinGrimNemesis(final SorinGrimNemesis card) {
        super(card);
    }
    
    @Override
    public SorinGrimNemesis copy() {
        return new SorinGrimNemesis(this);
    }
}

class SorinGrimNemesisRevealEffect extends OneShotEffect {
    
    public SorinGrimNemesisRevealEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library and put that card into your hand. Each opponent loses life equal to its mana value";
    }
    
    public SorinGrimNemesisRevealEffect(final SorinGrimNemesisRevealEffect effect) {
        super(effect);
    }
    
    @Override
    public SorinGrimNemesisRevealEffect copy() {
        return new SorinGrimNemesisRevealEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        
        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                controller.revealCards("Sorin, Grim Nemesis", cards, game);
                
                if (controller.moveCards(card, Zone.HAND, source, game)) {
                    for (UUID playerId : game.getOpponents(source.getControllerId())) {
                        if (card.getManaValue() > 0) {
                            Player opponent = game.getPlayer(playerId);
                            if (opponent != null) {
                                opponent.loseLife(card.getManaValue(), game, source, false);
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}

class SorinTokenEffect extends OneShotEffect {
    
    SorinTokenEffect() {
        super(Outcome.GainLife);
        staticText = "Create a number of 1/1 black Vampire Knight creature tokens with lifelink equal to the highest life total among all players";
    }
    
    private SorinTokenEffect(final SorinTokenEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        int maxLife = 0;
        PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
        for (UUID pid : playerList) {
            Player p = game.getPlayer(pid);
            if (p != null) {
                if (maxLife < p.getLife()) {
                    maxLife = p.getLife();
                }
            }
        }
        new CreateTokenEffect(new VampireKnightToken(), maxLife).apply(game, source);
        return true;
    }
    
    @Override
    public SorinTokenEffect copy() {
        return new SorinTokenEffect(this);
    }
}
