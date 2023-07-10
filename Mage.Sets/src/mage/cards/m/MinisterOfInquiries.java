
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class MinisterOfInquiries extends CardImpl {

    public MinisterOfInquiries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Minister of Inquiries enters the the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // {T}. Pay {E}: Target player puts the top three cards of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsTargetEffect(3), new TapSourceCost());
        ability.addCost(new PayEnergyCost(1));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private MinisterOfInquiries(final MinisterOfInquiries card) {
        super(card);
    }

    @Override
    public MinisterOfInquiries copy() {
        return new MinisterOfInquiries(this);
    }
}
