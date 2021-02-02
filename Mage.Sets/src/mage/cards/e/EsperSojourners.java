
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class EsperSojourners extends CardImpl {

    public EsperSojourners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{W}{U}{B}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);


        

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When you cycle Esper Sojourners or it dies, you may tap or untap target permanent.
        Ability ability1 = new CycleTriggeredAbility(new MayTapOrUntapTargetEffect());
        Ability ability2 = new DiesSourceTriggeredAbility(new MayTapOrUntapTargetEffect());
        ability1.addTarget(new TargetPermanent());
        ability2.addTarget(new TargetPermanent());
        this.addAbility(ability1);
        this.addAbility(ability2);
        
        // Cycling {2}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}{U}")));
    }

    private EsperSojourners(final EsperSojourners card) {
        super(card);
    }

    @Override
    public EsperSojourners copy() {
        return new EsperSojourners(this);
    }
}
