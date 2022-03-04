
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.GiantBaitingGiantWarriorToken;

/**
 *
 * @author jeffwadsworth
 */
public final class Giantbaiting extends CardImpl {

    public Giantbaiting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R/G}");

        // Create a 4/4 red and green Giant Warrior creature token with haste. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GiantbaitingEffect());

        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.NONE));

    }

    private Giantbaiting(final Giantbaiting card) {
        super(card);
    }

    @Override
    public Giantbaiting copy() {
        return new Giantbaiting(this);
    }
}

class GiantbaitingEffect extends OneShotEffect {

    public GiantbaitingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 4/4 red and green Giant Warrior creature token with haste. Exile it at the beginning of the next end step";
    }

    public GiantbaitingEffect(final GiantbaitingEffect effect) {
        super(effect);
    }

    @Override
    public GiantbaitingEffect copy() {
        return new GiantbaitingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new GiantBaitingGiantWarriorToken());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }
}
