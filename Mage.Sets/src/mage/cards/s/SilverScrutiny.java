package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CastAsThoughItHadFlashIfConditionAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 *
 * @author awjackson
 */
public final class SilverScrutiny extends CardImpl {

    public SilverScrutiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // You may cast Silver Scrutiny as though it had flash if X is 3 or less.
        this.addAbility(new CastAsThoughItHadFlashIfConditionAbility(
                SilverScrutinyCondition.instance,
                "You may cast {this} as though it had flash if X is 3 or less."
        ));

        // Draw X cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));
    }

    private SilverScrutiny(final SilverScrutiny card) {
        super(card);
    }

    @Override
    public SilverScrutiny copy() {
        return new SilverScrutiny(this);
    }
}

enum SilverScrutinyCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (spell == null) {
            return false;
        }
        return spell.getStackAbility().getManaCostsToPay().getX() < 4;
    }
}
