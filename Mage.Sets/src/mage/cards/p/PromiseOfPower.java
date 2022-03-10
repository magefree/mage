package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.DemonFlyingToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PromiseOfPower extends CardImpl {

    public PromiseOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}{B}");

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);

        // - You draw five cards and you lose 5 life.
        Effect effect = new DrawCardSourceControllerEffect(5);
        effect.setText("You draw five cards");
        this.getSpellAbility().addEffect(effect);
        effect = new LoseLifeSourceControllerEffect(5);
        effect.setText("and you lose 5 life");
        this.getSpellAbility().addEffect(effect);

        // - Create an X/X black Demon creature token with flying, where X is the number of cards in your hand.
        Mode mode = new Mode(new PromiseOfPowerEffect());
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {4}
        this.addAbility(new EntwineAbility("{4}"));
    }

    private PromiseOfPower(final PromiseOfPower card) {
        super(card);
    }

    @Override
    public PromiseOfPower copy() {
        return new PromiseOfPower(this);
    }
}

class PromiseOfPowerEffect extends OneShotEffect {

    public PromiseOfPowerEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Create an X/X black Demon creature token with flying, where X is the number of cards in your hand";
    }

    public PromiseOfPowerEffect(PromiseOfPowerEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return new CreateTokenEffect(new DemonFlyingToken(controller.getHand().size())).apply(game, source);
        }
        return false;
    }

    @Override
    public PromiseOfPowerEffect copy() {
        return new PromiseOfPowerEffect(this);
    }

}
