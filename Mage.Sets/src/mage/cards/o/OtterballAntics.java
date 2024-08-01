package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.OtterProwessToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OtterballAntics extends CardImpl {

    public OtterballAntics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Create a 1/1 blue and red Otter creature token with prowess. If this spell was cast from anywhere other than your hand, put a +1/+1 counter on that creature.
        this.getSpellAbility().addEffect(new OtterballAnticsEffect());

        // Flashback {3}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{U}")));
    }

    private OtterballAntics(final OtterballAntics card) {
        super(card);
    }

    @Override
    public OtterballAntics copy() {
        return new OtterballAntics(this);
    }
}

class OtterballAnticsEffect extends OneShotEffect {

    OtterballAnticsEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 blue and red Otter creature token with prowess. " +
                "If this spell was cast from anywhere other than your hand, put a +1/+1 counter on that creature";
    }

    private OtterballAnticsEffect(final OtterballAnticsEffect effect) {
        super(effect);
    }

    @Override
    public OtterballAnticsEffect copy() {
        return new OtterballAnticsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new OtterProwessToken();
        token.putOntoBattlefield(1, game, source);
        Spell spell = Optional
                .ofNullable(source.getSourceObjectIfItStillExists(game))
                .filter(Spell.class::isInstance)
                .map(Spell.class::cast)
                .orElse(null);
        if (spell == null || Zone.HAND.match(spell.getFromZone())) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
        }
        return true;
    }
}
