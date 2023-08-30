
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author anonymous
 */
public final class SeekerOfInsight extends CardImpl {

    public SeekerOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Draw a card, then discard a card. Activate this ability only if you've cast a noncreature spell this turn.
        this.addAbility(
                new ActivateIfConditionActivatedAbility(
                        Zone.BATTLEFIELD,
                        new DrawDiscardControllerEffect(),
                        new TapSourceCost(),
                        new CastNonCreatureSpellCondition()));
    }

    private SeekerOfInsight(final SeekerOfInsight card) {
        super(card);
    }

    @Override
    public SeekerOfInsight copy() {
        return new SeekerOfInsight(this);
    }
}

class CastNonCreatureSpellCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            List<Spell> spellsCast = watcher.getSpellsCastThisTurn(source.getControllerId());
            if (spellsCast != null) {
                for (Spell spell : spellsCast) {
                    if (!spell.isCreature(game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you've cast a noncreature spell this turn";
    }
}
