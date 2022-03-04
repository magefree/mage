package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritGreenToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class InvokeTheAncients extends CardImpl {

    public InvokeTheAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{G}{G}{G}");

        // Create two 4/5 green Spirit creature tokens. For each of them, put your choice of a vigilance counter, a reach counter, or a trample counter on it.
        this.getSpellAbility().addEffect(new InvokeTheAncientsEffect());
    }

    private InvokeTheAncients(final InvokeTheAncients card) {
        super(card);
    }

    @Override
    public InvokeTheAncients copy() {
        return new InvokeTheAncients(this);
    }
}

class InvokeTheAncientsEffect extends OneShotEffect {

    private static final Token token = new SpiritGreenToken();
    private static final Set<String> choices = Arrays.asList(
            "Vigilance", "Reach", "Trample"
    ).stream().collect(Collectors.toSet());

    InvokeTheAncientsEffect() {
        super(Outcome.Benefit);
        staticText = "create two 4/5 green Spirit creature tokens. For each of them, " +
                "put your choice of a vigilance counter, a reach counter, or a trample counter on it";
    }

    private InvokeTheAncientsEffect(final InvokeTheAncientsEffect effect) {
        super(effect);
    }

    @Override
    public InvokeTheAncientsEffect copy() {
        return new InvokeTheAncientsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        token.putOntoBattlefield(2, game, source, source.getControllerId());
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent == null) {
                continue;
            }
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose vigilance, reach, or trample counter");
            choice.setChoices(choices);
            player.choose(outcome, choice, game);
            String chosen = choice.getChoice();
            if (chosen != null) {
                permanent.addCounters(CounterType.findByName(
                        chosen.toLowerCase(Locale.ENGLISH)
                ).createInstance(), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
