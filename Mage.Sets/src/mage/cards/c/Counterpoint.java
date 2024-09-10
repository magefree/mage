package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Counterpoint extends CardImpl {

    public Counterpoint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{B}");

        // Counter target spell. You may cast a creature, instant, sorcery, or planeswalker spell from your graveyard with mana value less than or equal to that spell's mana value without paying its mana cost.
        this.getSpellAbility().addEffect(new CounterpointEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Counterpoint(final Counterpoint card) {
        super(card);
    }

    @Override
    public Counterpoint copy() {
        return new Counterpoint(this);
    }
}

class CounterpointEffect extends OneShotEffect {

    private static final FilterCard baseFilter = new FilterCard();

    static {
        baseFilter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    CounterpointEffect() {
        super(Outcome.Benefit);
        staticText = "counter target spell. You may cast a creature, instant, sorcery, or planeswalker spell from " +
                "your graveyard with mana value less than or equal to that spell's mana value without paying its mana cost";
    }

    private CounterpointEffect(final CounterpointEffect effect) {
        super(effect);
    }

    @Override
    public CounterpointEffect copy() {
        return new CounterpointEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (player == null || spell == null) {
            return false;
        }
        int mv = spell.getManaValue();
        game.getStack().counter(spell.getId(), source, game);
        Cards cards = new CardsImpl(player.getGraveyard());
        FilterCard filter = baseFilter.copy();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, mv + 1));
        CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter);
        return true;
    }
}
