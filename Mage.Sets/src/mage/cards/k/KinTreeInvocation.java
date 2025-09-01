
package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SpiritWarriorToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KinTreeInvocation extends CardImpl {

    public KinTreeInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{G}");

        // Create an X/X black and green Spirit Warrior creature token, where X is the greatest toughness among creatures you control.
        this.getSpellAbility().addEffect(new KinTreeInvocationCreateTokenEffect());
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.TOUGHNESS_CONTROLLED_CREATURES.getHint());
    }

    private KinTreeInvocation(final KinTreeInvocation card) {
        super(card);
    }

    @Override
    public KinTreeInvocation copy() {
        return new KinTreeInvocation(this);
    }
}

class KinTreeInvocationCreateTokenEffect extends OneShotEffect {

    KinTreeInvocationCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X black and green Spirit Warrior creature token, where X is the greatest toughness among creatures you control";
    }

    private KinTreeInvocationCreateTokenEffect(final KinTreeInvocationCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public KinTreeInvocationCreateTokenEffect copy() {
        return new KinTreeInvocationCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = GreatestAmongPermanentsValue.TOUGHNESS_CONTROLLED_CREATURES.calculate(game, source, this);
        return (new CreateTokenEffect(new SpiritWarriorToken(value))).apply(game, source);
    }

}
