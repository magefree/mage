
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ChokingTethers extends CardImpl {

    public ChokingTethers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Tap up to four target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 4));
        
        // Cycling {1}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{U}")));
        
        // When you cycle Choking Tethers, you may tap target creature.
        Ability ability = new CycleTriggeredAbility(new TapTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(1, 1));
        this.addAbility(ability);
    }

    private ChokingTethers(final ChokingTethers card) {
        super(card);
    }

    @Override
    public ChokingTethers copy() {
        return new ChokingTethers(this);
    }
}
