package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EssenceBacklash extends CardImpl {

    public EssenceBacklash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{R}");

        // Counter target creature spell. Essence Backlash deals damage equal to that spell's power to its controller.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
        this.getSpellAbility().addEffect(new EssenceBacklashEffect());
    }

    private EssenceBacklash(final EssenceBacklash card) {
        super(card);
    }

    @Override
    public EssenceBacklash copy() {
        return new EssenceBacklash(this);
    }
}

class EssenceBacklashEffect extends OneShotEffect {

    public EssenceBacklashEffect() {
        super(Outcome.Damage);
        staticText = "Counter target creature spell. {this} deals damage equal to that spell's power to its controller";
    }

    private EssenceBacklashEffect(final EssenceBacklashEffect effect) {
        super(effect);
    }

    @Override
    public EssenceBacklashEffect copy() {
        return new EssenceBacklashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (spell != null) {
            Player spellController = game.getPlayer(spell.getControllerId());

            result = game.getStack().counter(source.getFirstTarget(), source, game);
            if (spellController != null) {
                spellController.damage(spell.getPower().getValue(), source.getSourceId(), source, game);
            }
        }
        return result;
    }
}
