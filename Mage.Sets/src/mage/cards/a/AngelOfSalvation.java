
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PreventDamageToTargetMultiAmountEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author LevelX2
 */
public final class AngelOfSalvation extends CardImpl {

    public AngelOfSalvation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flash; convoke
        this.addAbility(FlashAbility.getInstance());
        this.addAbility(new ConvokeAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Angel of Salvation enters the battlefield, prevent the next 5 damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 5));
        ability.addTarget(new TargetAnyTargetAmount(5));
        this.addAbility(ability);
    }

    private AngelOfSalvation(final AngelOfSalvation card) {
        super(card);
    }

    @Override
    public AngelOfSalvation copy() {
        return new AngelOfSalvation(this);
    }
}
