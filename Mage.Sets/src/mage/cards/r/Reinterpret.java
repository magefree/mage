package mage.cards.r;

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
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Reinterpret extends CardImpl {

    public Reinterpret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{R}");

        // Counter target spell. You may cast a spell with an equal or lesser mana value from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new ReinterpretEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Reinterpret(final Reinterpret card) {
        super(card);
    }

    @Override
    public Reinterpret copy() {
        return new Reinterpret(this);
    }
}

class ReinterpretEffect extends OneShotEffect {

    ReinterpretEffect() {
        super(Outcome.Benefit);
        staticText = "counter target spell. You may cast a spell with an " +
                "equal or lesser mana value from your hand without paying its mana cost";
    }

    private ReinterpretEffect(final ReinterpretEffect effect) {
        super(effect);
    }

    @Override
    public ReinterpretEffect copy() {
        return new ReinterpretEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (spell == null || controller == null) {
            return false;
        }
        int manaValue = spell.getManaValue();
        spell.counter(source, game);
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(
                ComparisonType.FEWER_THAN, manaValue + 1
        ));
        CardUtil.castSpellWithAttributesForFree(controller, source, game, controller.getHand(), filter);
        return true;
    }
}
