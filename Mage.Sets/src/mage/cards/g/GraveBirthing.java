
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.EldraziScionToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class GraveBirthing extends CardImpl {

    public GraveBirthing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Target opponent exiles a card from their graveyard. You create a 1/1 colorless Eldrazi Scion creature token. It has "Sacrifice this creature: Add {C}."
        this.getSpellAbility().addEffect(new GraveBirthingEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        Effect effect = new CreateTokenEffect(new EldraziScionToken());
        effect.setText("You create a 1/1 colorless Eldrazi Scion creature token. It has \"Sacrifice this creature: Add {C}.\"<br>");
        this.getSpellAbility().addEffect(effect);        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private GraveBirthing(final GraveBirthing card) {
        super(card);
    }

    @Override
    public GraveBirthing copy() {
        return new GraveBirthing(this);
    }
}

class GraveBirthingEffect extends OneShotEffect {

    public GraveBirthingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent exiles a card from their graveyard";
    }

    public GraveBirthingEffect(final GraveBirthingEffect effect) {
        super(effect);
    }

    @Override
    public GraveBirthingEffect copy() {
        return new GraveBirthingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            Target target = new TargetCardInYourGraveyard();
            target.setNotTarget(true);
            opponent.chooseTarget(outcome, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            opponent.moveCards(card, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}
