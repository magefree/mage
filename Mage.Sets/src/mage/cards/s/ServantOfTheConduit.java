
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ServantOfTheConduit extends CardImpl {

    public ServantOfTheConduit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Servant of the Conduit enters teh battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // {T}, Pay {E}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new TapSourceCost());
        ability.addCost(new PayEnergyCost(1));
        this.addAbility(ability);
    }

    private ServantOfTheConduit(final ServantOfTheConduit card) {
        super(card);
    }

    @Override
    public ServantOfTheConduit copy() {
        return new ServantOfTheConduit(this);
    }
}
