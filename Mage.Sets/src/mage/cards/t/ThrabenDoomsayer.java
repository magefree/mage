package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class ThrabenDoomsayer extends CardImpl {

    public ThrabenDoomsayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Create a 1/1 white Human creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new HumanToken()), new TapSourceCost()));
        // Fateful hour - As long as you have 5 or less life, other creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, true),
                FatefulHourCondition.instance, "<br><i>Fateful hour</i> &mdash; As long as you have 5 or less life, other creatures you control get +2/+2")));
    }

    private ThrabenDoomsayer(final ThrabenDoomsayer card) {
        super(card);
    }

    @Override
    public ThrabenDoomsayer copy() {
        return new ThrabenDoomsayer(this);
    }
}
