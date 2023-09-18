
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author LevelX2
 */
public final class AngelOfTheDireHour extends CardImpl {

    public AngelOfTheDireHour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Angel of the Dire Hour enters the battlefield, if you cast it from your hand, exile all attacking creatures.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ExileAllEffect(StaticFilters.FILTER_ATTACKING_CREATURES), false),
                CastFromHandSourcePermanentCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, exile all attacking creatures."),
                new CastFromHandWatcher());
    }

    private AngelOfTheDireHour(final AngelOfTheDireHour card) {
        super(card);
    }

    @Override
    public AngelOfTheDireHour copy() {
        return new AngelOfTheDireHour(this);
    }
}
