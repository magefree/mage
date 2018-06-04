
package mage.cards.m;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author LevelX2
 */
public final class MemoryPlunder extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public MemoryPlunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U/B}{U/B}{U/B}{U/B}");

        // You may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost.
        this.getSpellAbility().addEffect(new MemoryPlunderEffect());
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter));

    }

    public MemoryPlunder(final MemoryPlunder card) {
        super(card);
    }

    @Override
    public MemoryPlunder copy() {
        return new MemoryPlunder(this);
    }
}

class MemoryPlunderEffect extends OneShotEffect {

    public MemoryPlunderEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost";
    }

    public MemoryPlunderEffect(final MemoryPlunderEffect effect) {
        super(effect);
    }

    @Override
    public MemoryPlunderEffect copy() {
        return new MemoryPlunderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null && player.chooseUse(Outcome.Benefit, "Cast " + card.getName() + " without paying cost?", source, game)) {
                player.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
            }
        }
        return false;
    }
}
