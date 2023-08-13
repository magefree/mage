package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CompleteTheCircuit extends CardImpl {

    private static final FilterCard filter = new FilterCard("sorcery spells");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public CompleteTheCircuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // You may cast sorcery spells this turn as though they had flash.
        this.getSpellAbility().addEffect(new CastAsThoughItHadFlashAllEffect(Duration.EndOfTurn, filter));

        // When you next cast an instant or sorcery spell this turn, copy that spell twice. You may choose new targets for the copies.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new CopyNextSpellDelayedTriggeredAbility(
                        StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                        new CompleteTheCircuitEffect(), "When you next cast an instant or sorcery spell " +
                        "this turn, copy that spell twice. You may choose new targets for the copies."
                )
        ).concatBy("<br>"));
    }

    private CompleteTheCircuit(final CompleteTheCircuit card) {
        super(card);
    }

    @Override
    public CompleteTheCircuit copy() {
        return new CompleteTheCircuit(this);
    }
}

class CompleteTheCircuitEffect extends OneShotEffect {

    CompleteTheCircuitEffect() {
        super(Outcome.Benefit);
    }

    private CompleteTheCircuitEffect(final CompleteTheCircuitEffect effect) {
        super(effect);
    }

    @Override
    public CompleteTheCircuitEffect copy() {
        return new CompleteTheCircuitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true, 2);
            return true;
        }
        return false;
    }
}
