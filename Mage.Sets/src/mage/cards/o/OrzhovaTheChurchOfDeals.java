
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class OrzhovaTheChurchOfDeals extends CardImpl {

    public OrzhovaTheChurchOfDeals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.addAbility(new ColorlessManaAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), new ManaCostsImpl<>("{3}{W}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetPlayer(1));
        this.addAbility(ability);
    }

    private OrzhovaTheChurchOfDeals(final OrzhovaTheChurchOfDeals card) {
        super(card);
    }

    @Override
    public OrzhovaTheChurchOfDeals copy() {
        return new OrzhovaTheChurchOfDeals(this);
    }
}
