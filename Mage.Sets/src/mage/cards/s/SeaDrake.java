
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class SeaDrake extends CardImpl {

    public SeaDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Sea Drake enters the battlefield, return two target lands you control to their owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetPermanent(2, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS));
        this.addAbility(ability);
    }

    private SeaDrake(final SeaDrake card) {
        super(card);
    }

    @Override
    public SeaDrake copy() {
        return new SeaDrake(this);
    }
}
