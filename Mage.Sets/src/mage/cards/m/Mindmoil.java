
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Mindmoil extends CardImpl {

    public Mindmoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}");


        // Whenever you cast a spell, put the cards in your hand on the bottom of your library in any order, then draw that many cards.
        this.addAbility(new SpellCastControllerTriggeredAbility(new MindmoilEffect(), false));
    }

    private Mindmoil(final Mindmoil card) {
        super(card);
    }

    @Override
    public Mindmoil copy() {
        return new Mindmoil(this);
    }
}

class MindmoilEffect extends OneShotEffect {

    public MindmoilEffect() {
        super(Outcome.Neutral);
        staticText = "put the cards in your hand on the bottom of your library in any order, then draw that many cards";
    }

    private MindmoilEffect(final MindmoilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            int count = you.getHand().size();
            you.putCardsOnBottomOfLibrary(you.getHand(), game, source, true);
            you.drawCards(count, source, game);
        }
        return true;
    }

    @Override
    public MindmoilEffect copy() {
        return new MindmoilEffect(this);
    }
}