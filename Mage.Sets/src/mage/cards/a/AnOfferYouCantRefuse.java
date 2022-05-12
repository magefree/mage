package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnOfferYouCantRefuse extends CardImpl {

    public AnOfferYouCantRefuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target noncreature spell. Its controller creates two Treasure tokens.
        this.getSpellAbility().addEffect(new AnOfferYouCantRefuseEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
    }

    private AnOfferYouCantRefuse(final AnOfferYouCantRefuse card) {
        super(card);
    }

    @Override
    public AnOfferYouCantRefuse copy() {
        return new AnOfferYouCantRefuse(this);
    }
}

class AnOfferYouCantRefuseEffect extends OneShotEffect {

    AnOfferYouCantRefuseEffect() {
        super(Outcome.Benefit);
        staticText = "counter target noncreature spell. Its controller creates two Treasure tokens";
    }

    private AnOfferYouCantRefuseEffect(final AnOfferYouCantRefuseEffect effect) {
        super(effect);
    }

    @Override
    public AnOfferYouCantRefuseEffect copy() {
        return new AnOfferYouCantRefuseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        game.getStack().counter(spell.getId(), source, game);;
        new TreasureToken().putOntoBattlefield(2, game, source, spell.getControllerId());
        return true;
    }
}
