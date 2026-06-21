package mage.abilities.effects.common;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class PutIntoLibraryNFromTopTargetEffect extends OneShotEffect {

    private final int position;

    public PutIntoLibraryNFromTopTargetEffect(int position) {
        super(Outcome.Benefit);
        this.position = position;
    }

    private PutIntoLibraryNFromTopTargetEffect(final PutIntoLibraryNFromTopTargetEffect effect) {
        super(effect);
        this.position = effect.position;
    }

    @Override
    public PutIntoLibraryNFromTopTargetEffect copy() {
        return new PutIntoLibraryNFromTopTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .collect(Collectors.toCollection(CardsImpl::new));
        Player player = game.getPlayer(source.getControllerId());
        return player != null && !cards.isEmpty()
                && player.putCardsOnTopXOfLibrary(cards, game, source, position, true);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "put " + getTargetPointer().describeTargets(mode.getTargets(), "") +
                " into its owner's library " + CardUtil.numberToOrdinalText(position) + " from the top";
    }
}
