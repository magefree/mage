
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Rafbill
 */
public final class TempestOwl extends CardImpl {

    public TempestOwl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Kicker {4}{U}
        this.addAbility(new KickerAbility("{4}{U}"));

        // When Tempest Owl enters the battlefield, if it was kicked, tap up to three target permanents.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), false);
        ability.addTarget(new TargetPermanent(0, 3, new FilterPermanent(), false));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, tap up to three target permanents."));
    }

    private TempestOwl(final TempestOwl card) {
        super(card);
    }

    @Override
    public TempestOwl copy() {
        return new TempestOwl(this);
    }
}
