package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CompleatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author AhmadYProjects
 */
public final class JaceThePerfectedMind extends CardImpl {

    public JaceThePerfectedMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U/P}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);
        this.setStartingLoyalty(5);

        // Compleated
        this.addAbility(CompleatedAbility.getInstance());

        // +1: Until your next turn, up to one target creature gets -3/-0.
        Effect effect1 = new BoostTargetEffect(-3,0, Duration.UntilYourNextTurn);
        effect1.setText("Until your next turn, up to one target creature gets -3/-0");
        Ability ability1 = new LoyaltyAbility(effect1, 1);
        ability1.addTarget(new TargetCreaturePermanent(0,1));
        this.addAbility(ability1);
        // -2: Target player mills three cards. Then if a graveyard has twenty or more cards in it, you draw three cards. Otherwise, you draw a card.
        Ability ability2 = new LoyaltyAbility(new JaceThePerfectedMindEffect(), -2);
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);
        // -X: Target player mills three times X cards.
        Ability ability3 = new LoyaltyAbility(new MillCardsTargetEffect(new MultipliedValue
                (GetXLoyaltyValue.instance, 3)).setText("Target player mills three times X cards."));
        ability3.addTarget(new TargetPlayer());
        this.addAbility(ability3);
    }

    private JaceThePerfectedMind(final JaceThePerfectedMind card) {
        super(card);
    }

    @Override
    public JaceThePerfectedMind copy() {
        return new JaceThePerfectedMind(this);
    }
}

class JaceThePerfectedMindEffect extends OneShotEffect{
    public JaceThePerfectedMindEffect(){
        super(Outcome.DrawCard);
        staticText = "Target player mills three cards. Then if a graveyard has twenty or more cards in it, you draw " +
                "three cards. Otherwise, you draw a card.";
    }

    public JaceThePerfectedMindEffect(JaceThePerfectedMindEffect effect){
        super(effect);
    }

    @Override
    public JaceThePerfectedMindEffect copy() {
        return new JaceThePerfectedMindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source){
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        int x = 1;
        targetPlayer.millCards(3,source,game);
        for (UUID playerId: game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.getGraveyard().size() >= 20) {
                    x = 3;
                    break;
                }
            }
        }
        sourcePlayer.drawCards(x, source, game);
        return true;
    }


}