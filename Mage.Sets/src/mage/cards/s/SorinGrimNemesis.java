
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
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

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(6));

        // +1: Reveal the top card of your library and put that card into your hand. Each opponent loses life equal to its converted mana cost.
        this.addAbility(new LoyaltyAbility(new SorinGrimNemesisRevealEffect(), 1));

        // -X: Sorin, Grim Nemesis deals X damage to target creature or planeswalker and you gain X life.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(SorinXValue.getDefault()));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        ability.addEffect(new GainLifeEffect(SorinXValue.getDefault()));
        this.addAbility(ability);

        // -9: Create a number of 1/1 black Vampire Knight creature tokens with lifelink equal to the highest life total among all players.
        this.addAbility(new LoyaltyAbility(new SorinTokenEffect(), -9));
    }

    public SorinGrimNemesis(final SorinGrimNemesis card) {
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
        this.staticText = "reveal the top card of your library and put that card into your hand. Each opponent loses life equal to that card's converted mana cost";
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
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.getLibrary().hasCards()) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                player.revealCards("Sorin, Grim Nemesis", cards, game);

                if (card.moveToZone(Zone.HAND, source.getSourceId(), game, false)) {
                    for (UUID playerId : game.getOpponents(source.getControllerId())) {
                        if (card.getConvertedManaCost() > 0) {
                            Player opponent = game.getPlayer(playerId);
                            if (opponent != null) {
                                opponent.loseLife(card.getConvertedManaCost(), game, false);
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

    class SorinXValue implements DynamicValue {

        private static final SorinXValue defaultValue = new SorinXValue();

        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            for (Cost cost : sourceAbility.getCosts()) {
                if (cost instanceof PayVariableLoyaltyCost) {
                    return ((PayVariableLoyaltyCost) cost).getAmount();
                }
            }
            return 0;
        }

        @Override
        public DynamicValue copy() {
            return defaultValue;
        }

        @Override
        public String getMessage() {
            return "";
        }

        @Override
        public String toString() {
            return "X";
        }

        public static SorinXValue getDefault() {
            return defaultValue;
        }
    }

    class SorinTokenEffect extends OneShotEffect {

        SorinTokenEffect() {
            super(Outcome.GainLife);
            staticText = "Create a number of 1/1 black Vampire Knight creature tokens with lifelink equal to the highest life total among all players";
        }

        SorinTokenEffect(final SorinTokenEffect effect) {
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
