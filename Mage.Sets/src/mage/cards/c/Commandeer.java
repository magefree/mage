
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class Commandeer extends CardImpl {

    private static final FilterCard filter = new FilterCard("blue cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public Commandeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{U}{U}");


        // You may exile two blue cards from your hand rather than pay Commandeer's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(2, filter))));

        // Gain control of target noncreature spell. You may choose new targets for it.
        this.getSpellAbility().addEffect(new CommandeerEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
    }

    private Commandeer(final Commandeer card) {
        super(card);
    }

    @Override
    public Commandeer copy() {
        return new Commandeer(this);
    }
}

class CommandeerEffect extends OneShotEffect {

    public CommandeerEffect() {
        super(Outcome.Benefit);
        this.staticText = "Gain control of target noncreature spell. " +
                "You may choose new targets for it. " +
                "<i> (If that spell is an artifact, enchantment, or planeswalker, " +
                "the permanent enters the battlefield under your control.)</i>";
    }

    public CommandeerEffect(final CommandeerEffect effect) {
        super(effect);
    }

    @Override
    public CommandeerEffect copy() {
        return new CommandeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (controller != null && spell != null) {
            spell.setControllerId(controller.getId());
            spell.chooseNewTargets(game, controller.getId(), false, false, null);
            return true;
        }
        return false;
    }
}
