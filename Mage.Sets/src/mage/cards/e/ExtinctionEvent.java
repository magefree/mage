package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostParityPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ExtinctionEvent extends CardImpl {

    public ExtinctionEvent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Choose odd or even. Exile each creature with a converted mana cost of the chosen value.
        this.getSpellAbility().addEffect(new ExtinctionEventEffect());
    }

    private ExtinctionEvent(final ExtinctionEvent card) {
        super(card);
    }

    @Override
    public ExtinctionEvent copy() {
        return new ExtinctionEvent(this);
    }
}

class ExtinctionEventEffect extends OneShotEffect {

    private static final FilterPermanent evenFilter = new FilterCreaturePermanent();
    private static final FilterPermanent oddFilter = new FilterCreaturePermanent();

    static {
        evenFilter.add(ConvertedManaCostParityPredicate.EVEN);
        oddFilter.add(ConvertedManaCostParityPredicate.ODD);
    }

    ExtinctionEventEffect() {
        super(Outcome.Benefit);
        staticText = "Choose odd or even. Exile each creature with a converted mana cost of the chosen value.";
    }

    private ExtinctionEventEffect(final ExtinctionEventEffect effect) {
        super(effect);
    }

    @Override
    public ExtinctionEventEffect copy() {
        return new ExtinctionEventEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterPermanent filter = player.chooseUse(
                outcome, "Odd or even?", null,
                "Odd", "Even", source, game
        ) ? oddFilter : evenFilter;
        return player.moveCards(
                game.getBattlefield().getActivePermanents(
                        filter, source.getSourceId(), game
                ).stream().collect(Collectors.toSet()), Zone.EXILED, source, game
        );
    }
}
