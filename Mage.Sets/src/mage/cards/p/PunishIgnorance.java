
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class PunishIgnorance extends CardImpl {

    public PunishIgnorance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{U}{U}{B}");


        // Counter target spell. Its controller loses 3 life and you gain 3 life.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(3));
        Effect effect = new GainLifeEffect(3);
        effect.setText("and you gain 3 life");
        this.getSpellAbility().addEffect(effect);
    }

    private PunishIgnorance(final PunishIgnorance card) {
        super(card);
    }

    @Override
    public PunishIgnorance copy() {
        return new PunishIgnorance(this);
    }
}
