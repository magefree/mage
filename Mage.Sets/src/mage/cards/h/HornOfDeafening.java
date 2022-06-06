
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author ilcartographer
 */
public final class HornOfDeafening extends CardImpl {

    public HornOfDeafening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, {tap}: Prevent all combat damage that would be dealt by target creature this turn.
        Effect effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage that would be dealt by target creature this turn.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HornOfDeafening(final HornOfDeafening card) {
        super(card);
    }

    @Override
    public HornOfDeafening copy() {
        return new HornOfDeafening(this);
    }
}
