package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 *
 * @author miesma
 */
public final class UncoverTheMoonLetters extends CardImpl {

    public UncoverTheMoonLetters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Whenever you cast a noncreature spell,
        // you may draw X cards,
        // where X is the amount of mana spent to cast that spell.
        // If you do, discard two cards.
        Ability ability = new SpellCastControllerTriggeredAbility(new UncoverTheMoonLettersEffect(),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false, SetTargetPointer.SPELL);
        this.addAbility(ability);
    }

    private UncoverTheMoonLetters(final UncoverTheMoonLetters card) {
        super(card);
    }

    @Override
    public UncoverTheMoonLetters copy() {
        return new UncoverTheMoonLetters(this);
    }
}

class UncoverTheMoonLettersEffect extends OneShotEffect {

    UncoverTheMoonLettersEffect() {
        super(Outcome.DrawCard);
        staticText = "may draw X cards, " +
                "where X is the amount of mana spent to cast that spell. " +
                "If you do, discard two cards.";
    }

    private UncoverTheMoonLettersEffect(final UncoverTheMoonLettersEffect effect) {
        super(effect);
    }

    @Override
    public UncoverTheMoonLettersEffect copy() {
        return new UncoverTheMoonLettersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            Player player = game.getPlayer(source.getControllerId());
            int value = ManaPaidSourceWatcher.getTotalPaid(spell.getId(), game);
            if (player != null) {
                // Ask to use with value info
                if (player.chooseUse(Outcome.DrawCard,
                        "Draw " + value + " cards? If you do, discard two cards.", source, game)) {
                    return new DrawDiscardControllerEffect(value, 2).apply(game, source);
                }
            }
        }
        return false;
    }
}
