package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.AscendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.DetectiveToken;

/**
 * @author Cguy7777
 */
public final class DetectiveOfTheMonth extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.DETECTIVE);

    public DetectiveOfTheMonth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Ascend
        this.addAbility(new AscendAbility());

        // As long as you have the city's blessing, Detectives you control can't be blocked.
        Effect effect = new ConditionalRestrictionEffect(
                new CantBeBlockedAllEffect(filter, Duration.WhileOnBattlefield),
                CitysBlessingCondition.instance,
                "as long as you have the city's blessing, Detectives you control can't be blocked");
        this.addAbility(new SimpleStaticAbility(effect).addHint(CitysBlessingHint.instance));

        // Whenever you draw your second card each turn, create a 2/2 white and blue Detective creature token.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new CreateTokenEffect(new DetectiveToken()), false, 2));
    }

    private DetectiveOfTheMonth(final DetectiveOfTheMonth card) {
        super(card);
    }

    @Override
    public DetectiveOfTheMonth copy() {
        return new DetectiveOfTheMonth(this);
    }
}
