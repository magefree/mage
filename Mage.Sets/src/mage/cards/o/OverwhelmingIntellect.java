
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class OverwhelmingIntellect extends CardImpl {

    public OverwhelmingIntellect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}{U}");

        // Counter target creature spell. Draw cards equal to that spell's converted mana cost.
        this.getSpellAbility().addTarget(new TargetSpell(new FilterCreatureSpell()));
        this.getSpellAbility().addEffect(new OverwhelmingIntellectEffect());

    }

    private OverwhelmingIntellect(final OverwhelmingIntellect card) {
        super(card);
    }

    @Override
    public OverwhelmingIntellect copy() {
        return new OverwhelmingIntellect(this);
    }
}

class OverwhelmingIntellectEffect extends OneShotEffect {

    public OverwhelmingIntellectEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target creature spell. Draw cards equal to that spell's mana value";
    }

    private OverwhelmingIntellectEffect(final OverwhelmingIntellectEffect effect) {
        super(effect);
    }

    @Override
    public OverwhelmingIntellectEffect copy() {
        return new OverwhelmingIntellectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (controller != null && spell != null) {
            game.getStack().counter(source.getFirstTarget(), source, game);
            controller.drawCards(spell.getManaValue(), source, game);
            return true;
        }
        return false;
    }
}
