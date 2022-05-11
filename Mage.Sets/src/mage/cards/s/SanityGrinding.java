package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SanityGrinding extends CardImpl {

    public SanityGrinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}{U}");

        // Chroma - Reveal the top ten cards of your library. For each blue mana symbol in the mana costs of the revealed cards, target opponent puts the top card of their library into their graveyard. Then put the cards you revealed this way on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new SanityGrindingEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    private SanityGrinding(final SanityGrinding card) {
        super(card);
    }

    @Override
    public SanityGrinding copy() {
        return new SanityGrinding(this);
    }
}

class SanityGrindingEffect extends OneShotEffect {

    public SanityGrindingEffect() {
        super(Outcome.Neutral);
        staticText = "<i>Chroma</i> &mdash; Reveal the top ten cards of your library. " +
                "For each blue mana symbol in the mana costs of the revealed cards, target opponent mills a card. " +
                "Then put the cards you revealed this way on the bottom of your library in any order";
    }

    public SanityGrindingEffect(final SanityGrindingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards revealed = new CardsImpl();
        revealed.addAll(controller.getLibrary().getTopCards(game, 10));
        controller.revealCards(sourceObject.getIdName(), revealed, game);
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if (targetOpponent != null) {
            targetOpponent.millCards(new ChromaSanityGrindingCount(revealed).calculate(game, source, this), source, game);
        }
        return controller.putCardsOnBottomOfLibrary(revealed, game, source, true);
    }

    @Override
    public SanityGrindingEffect copy() {
        return new SanityGrindingEffect(this);
    }
}

class ChromaSanityGrindingCount implements DynamicValue {

    private final Cards revealed;

    public ChromaSanityGrindingCount(Cards revealed) {
        this.revealed = revealed;
    }

    public ChromaSanityGrindingCount(final ChromaSanityGrindingCount dynamicValue) {
        this.revealed = dynamicValue.revealed;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int chroma = 0;
        for (Card card : revealed.getCards(game)) {
            chroma += card.getManaCost().getMana().getBlue();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaSanityGrindingCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
