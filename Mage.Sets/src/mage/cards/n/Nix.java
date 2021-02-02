
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class Nix extends CardImpl {

    public Nix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target spell if no mana was spent to cast it.
        Effect effect = new ConditionalOneShotEffect(new CounterTargetEffect(), NoManaSpentToCastTargetCondition.instance);
        effect.setText("Counter target spell if no mana was spent to cast it");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell());

    }

    private Nix(final Nix card) {
        super(card);
    }

    @Override
    public Nix copy() {
        return new Nix(this);
    }
}

enum NoManaSpentToCastTargetCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        if (targetId != null) {
            StackObject stackObject = game.getStack().getStackObject(targetId);
            if (stackObject != null) {
                return stackObject.getStackAbility().getManaCostsToPay().getUsedManaToPay().count() == 0;
            }
        }
        return false;
    }
}
