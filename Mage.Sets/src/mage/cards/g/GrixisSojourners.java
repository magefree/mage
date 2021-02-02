
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInASingleGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class GrixisSojourners extends CardImpl {

    public GrixisSojourners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}{R}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.OGRE);



        
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When you cycle Grixis Sojourners or it dies, you may exile target card from a graveyard.
        Ability ability1 = new CycleTriggeredAbility(new ExileTargetEffect(), true);
        Ability ability2 = new DiesSourceTriggeredAbility(new ExileTargetEffect(), true);
        ability1.addTarget(new TargetCardInASingleGraveyard(1, 1, new FilterCard()));
        ability2.addTarget(new TargetCardInASingleGraveyard(1, 1, new FilterCard()));
        this.addAbility(ability1);
        this.addAbility(ability2);
        
        // Cycling {2}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}{B}")));
    }

    private GrixisSojourners(final GrixisSojourners card) {
        super(card);
    }

    @Override
    public GrixisSojourners copy() {
        return new GrixisSojourners(this);
    }
}
