package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class ReverseThePolarity extends CardImpl {

    public ReverseThePolarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Choose one --
        // * Counter all other spells.
        this.getSpellAbility().addEffect(new ReverseThePolarityCounterAllEffect());
        // * Switch each creature's power and toughness until end of turn.
        this.getSpellAbility().addMode(new Mode(new SwitchPowerToughnessAllEffect(Duration.EndOfTurn)));
        // * Creatures can't be blocked this turn.
        this.getSpellAbility().addMode(new Mode(new CantBeBlockedAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn)));
    }

    private ReverseThePolarity(final ReverseThePolarity card) {
        super(card);
    }

    @Override
    public ReverseThePolarity copy() {
        return new ReverseThePolarity(this);
    }
}
//Based on Counterflux/Swift Silence
class ReverseThePolarityCounterAllEffect extends OneShotEffect {

    ReverseThePolarityCounterAllEffect() {
        super(Outcome.Detriment);
        staticText = "Counter all other spells.";
    }

    private ReverseThePolarityCounterAllEffect(final ReverseThePolarityCounterAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Spell> spellsToCounter = new LinkedList<>();
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell && !stackObject.getId().equals(source.getSourceObject(game).getId())) {
                spellsToCounter.add((Spell) stackObject);
            }
        }
        for (Spell spell : spellsToCounter) {
            game.getStack().counter(spell.getId(), source, game);
        }
        return true;
    }

    @Override
    public ReverseThePolarityCounterAllEffect copy() {
        return new ReverseThePolarityCounterAllEffect(this);
    }

}