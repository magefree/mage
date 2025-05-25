
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.OozeToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MimingSlime extends CardImpl {

    public MimingSlime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Create an X/X green Ooze creature token, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new MimingSlimeCreateTokenEffect());
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint());
    }

    private MimingSlime(final MimingSlime card) {
        super(card);
    }

    @Override
    public MimingSlime copy() {
        return new MimingSlime(this);
    }
}

class MimingSlimeCreateTokenEffect extends OneShotEffect {

    MimingSlimeCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X green Ooze creature token, where X is the greatest power among creatures you control";
    }

    private MimingSlimeCreateTokenEffect(final MimingSlimeCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public MimingSlimeCreateTokenEffect copy() {
        return new MimingSlimeCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.calculate(game, source, this);
        return (new CreateTokenEffect(new OozeToken(value, value))).apply(game, source);
    }
}
