

package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class DispenseJustice extends CardImpl {

    public DispenseJustice (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        this.getSpellAbility().addEffect(new DispenseJusticeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public DispenseJustice (final DispenseJustice card) {
        super(card);
    }

    @Override
    public DispenseJustice copy() {
        return new DispenseJustice(this);
    }

}

class DispenseJusticeEffect extends OneShotEffect {

    private static final String effectText = "Target player sacrifices an attacking creature.\r\n\r\n"
            + "<i>Metalcraft</i> &mdash; That player sacrifices two attacking creatures instead if you control three or more artifacts";

    private static final FilterAttackingCreature filter = new FilterAttackingCreature();

    DispenseJusticeEffect ( ) {
        super(Outcome.Sacrifice);
        staticText = effectText;
    }

    DispenseJusticeEffect ( DispenseJusticeEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if ( MetalcraftCondition.instance.apply(game, source) ) {
            return new SacrificeEffect(filter, 2, effectText).apply(game, source);
        }
        else {
            return new SacrificeEffect(filter, 1, effectText).apply(game, source);
        }
    }

    @Override
    public DispenseJusticeEffect copy() {
        return new DispenseJusticeEffect(this);
    }

}
