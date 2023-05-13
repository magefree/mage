
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.command.emblems.ObNixilisOfTheBlackOathEmblem;
import mage.game.permanent.token.DemonToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ObNixilisOfTheBlackOath extends CardImpl {

    public ObNixilisOfTheBlackOath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIXILIS);

        this.setStartingLoyalty(3);

        // +2: Each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new LoyaltyAbility(new ObNixilisOfTheBlackOathEffect1(), 2));

        // -2: Create a 5/5 black Demon creature token with flying. You lose 2 life.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new CreateTokenEffect(new DemonToken()), -2);
        loyaltyAbility.addEffect(new LoseLifeSourceControllerEffect(2));
        this.addAbility(loyaltyAbility);

        // -8: You get an emblem with "{1}{B}, Sacrifice a creature: You gain X life and draw X cards, where X is the sacrificed creature's power."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ObNixilisOfTheBlackOathEmblem()), -8));

        // Ob Nixilis of the Black Oath can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private ObNixilisOfTheBlackOath(final ObNixilisOfTheBlackOath card) {
        super(card);
    }

    @Override
    public ObNixilisOfTheBlackOath copy() {
        return new ObNixilisOfTheBlackOath(this);
    }
}

class ObNixilisOfTheBlackOathEffect1 extends OneShotEffect {

    public ObNixilisOfTheBlackOathEffect1() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 1 life. You gain life equal to the life lost this way";
    }

    public ObNixilisOfTheBlackOathEffect1(final ObNixilisOfTheBlackOathEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int loseLife = 0;
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    loseLife += opponent.loseLife(1, game, source, false);
                }
            }
            controller.gainLife(loseLife, game, source);
            return true;

        }
        return false;
    }

    @Override
    public ObNixilisOfTheBlackOathEffect1 copy() {
        return new ObNixilisOfTheBlackOathEffect1(this);
    }

}
