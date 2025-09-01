package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SpellRupture extends CardImpl {

    public SpellRupture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {X}, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new SpellRuptureCounterUnlessPaysEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint());
    }

    private SpellRupture(final SpellRupture card) {
        super(card);
    }

    @Override
    public SpellRupture copy() {
        return new SpellRupture(this);
    }
}

class SpellRuptureCounterUnlessPaysEffect extends OneShotEffect {

    SpellRuptureCounterUnlessPaysEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell unless its controller pays {X}, where X is the greatest power among creatures you control";
    }

    private SpellRuptureCounterUnlessPaysEffect(final SpellRuptureCounterUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public SpellRuptureCounterUnlessPaysEffect copy() {
        return new SpellRuptureCounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        Player player = game.getPlayer(spell.getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (player == null || controller == null || sourceObject == null) {
            return false;
        }
        int maxPower = GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.calculate(game, source, this);
        Cost cost = ManaUtil.createManaCost(maxPower, true);
        if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + ", where X is " + maxPower + "? (otherwise " + spell.getName() + " will be countered)", source, game)
                && cost.pay(source, game, source, player.getId(), false)) {
            return true;
        }
        game.informPlayers(sourceObject.getName() + ": cost wasn't payed - countering " + spell.getName());
        return game.getStack().counter(source.getFirstTarget(), source, game);
    }
}