package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KindredDenial extends CardImpl {

    public KindredDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Counter target spell. Seek a card with the same mana value as that spell.
        this.getSpellAbility().addEffect(new KindredDenialEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private KindredDenial(final KindredDenial card) {
        super(card);
    }

    @Override
    public KindredDenial copy() {
        return new KindredDenial(this);
    }
}

class KindredDenialEffect extends OneShotEffect {

    KindredDenialEffect() {
        super(Outcome.Benefit);
        staticText = "counter target spell. Seek a card with the same mana value as that spell";
    }

    private KindredDenialEffect(final KindredDenialEffect effect) {
        super(effect);
    }

    @Override
    public KindredDenialEffect copy() {
        return new KindredDenialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = game.getSpell(source.getFirstTarget());
        if (player == null || spell == null) {
            return false;
        }
        int manaValue = spell.getManaValue();
        spell.counter(source, game);
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, manaValue));
        player.seekCard(filter, source, game);
        return true;
    }
}
