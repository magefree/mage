

package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class ScepterOfInsight extends CardImpl {

    public ScepterOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}{U}{U}");


        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{3}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ScepterOfInsight(final ScepterOfInsight card) {
        super(card);
    }

    @Override
    public ScepterOfInsight copy() {
        return new ScepterOfInsight(this);
    }

}
