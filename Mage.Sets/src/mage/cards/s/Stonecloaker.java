
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Stonecloaker extends CardImpl {

    public Stonecloaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.GARGOYLE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Stonecloaker enters the battlefield, return a creature you control to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(new FilterControlledCreaturePermanent()), false);

        this.addAbility(ability);
        // When Stonecloaker enters the battlefield, exile target card from a graveyard.
        ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect(), false);
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private Stonecloaker(final Stonecloaker card) {
        super(card);
    }

    @Override
    public Stonecloaker copy() {
        return new Stonecloaker(this);
    }
}
