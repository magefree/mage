package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.WhiteBlueBirdToken;
import mage.game.stack.Spell;

/**
 *
 * @author emerald000
 */
public final class Dovescape extends CardImpl {

    public Dovescape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W/U}{W/U}{W/U}");

        // Whenever a player casts a noncreature spell, counter that spell. That player creates X 1/1 white and blue Bird creature tokens with flying, where X is the spell's converted mana cost.
        this.addAbility(new SpellCastAllTriggeredAbility(new DovescapeEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false, SetTargetPointer.SPELL));
    }

    private Dovescape(final Dovescape card) {
        super(card);
    }

    @Override
    public Dovescape copy() {
        return new Dovescape(this);
    }
}

class DovescapeEffect extends OneShotEffect {

    DovescapeEffect() {
        super(Outcome.Benefit);
        this.staticText = "counter that spell. That player creates X 1/1 white and blue Bird creature tokens with flying, where X is the spell's mana value";
    }

    DovescapeEffect(final DovescapeEffect effect) {
        super(effect);
    }

    @Override
    public DovescapeEffect copy() {
        return new DovescapeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        int spellCMC = 0;
        UUID spellControllerID = null;
        if (spell != null) {
            spellCMC = spell.getManaValue();
            spellControllerID = spell.getControllerId();
            game.getStack().counter(spell.getId(), source, game);
        }
        new WhiteBlueBirdToken().putOntoBattlefield(spellCMC, game, source, spellControllerID);
        return true;
    }
}
