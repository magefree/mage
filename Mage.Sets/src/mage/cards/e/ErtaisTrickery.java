
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 *
 * @author LoneFox
 */
public final class ErtaisTrickery extends CardImpl {

    public ErtaisTrickery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Counter target spell if it was kicked.
        this.getSpellAbility().addEffect(new ErtaisTrickeryEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private ErtaisTrickery(final ErtaisTrickery card) {
        super(card);
    }

    @Override
    public ErtaisTrickery copy() {
        return new ErtaisTrickery(this);
    }
}

class ErtaisTrickeryEffect extends CounterTargetEffect {

    public ErtaisTrickeryEffect() {
        super();
        staticText = "Counter target spell if it was kicked.";
    }

    public ErtaisTrickeryEffect(final CounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public ErtaisTrickeryEffect copy() {
        return new ErtaisTrickeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell targetSpell = game.getStack().getSpell(source.getFirstTarget());
        if(targetSpell != null && KickedCondition.ONCE.apply(game, targetSpell.getSpellAbility())) {
            return super.apply(game, source);
        }
        return false;
    }

}
