package mage.cards.g;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.FaerieBlueBlackToken;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class GlenElendrasAnswer extends CardImpl {

    public GlenElendrasAnswer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Counter all spells your opponents control and all abilities your opponents control. Create a 1/1 blue and black Faerie creature token with flying for each spell and ability countered this way.
        this.getSpellAbility().addEffect(new GlenElendrasAnswerEffect());
    }

    private GlenElendrasAnswer(final GlenElendrasAnswer card) {
        super(card);
    }

    @Override
    public GlenElendrasAnswer copy() {
        return new GlenElendrasAnswer(this);
    }
}

class GlenElendrasAnswerEffect extends OneShotEffect {

    GlenElendrasAnswerEffect() {
        super(Outcome.Benefit);
        staticText = "counter all spells your opponents control and all abilities your opponents control. " +
                "Create a 1/1 blue and black Faerie creature token with flying for each spell and ability countered this way";
    }

    private GlenElendrasAnswerEffect(final GlenElendrasAnswerEffect effect) {
        super(effect);
    }

    @Override
    public GlenElendrasAnswerEffect copy() {
        return new GlenElendrasAnswerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        int count = game
                .getStack()
                .stream()
                .filter(stackObject -> opponents.contains(stackObject.getControllerId()))
                .collect(Collectors.toList())
                .stream()
                .map(MageItem::getId)
                .mapToInt(uuid -> game.getStack().counter(uuid, source, game) ? 1 : 0)
                .sum();
        if (count < 1) {
            return false;
        }
        new FaerieBlueBlackToken().putOntoBattlefield(count, game, source);
        return true;
    }
}
