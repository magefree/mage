package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.DragonToken2;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SkylineDespot extends CardImpl {

    public SkylineDespot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Skyline Despot enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // At the beginning of your upkeep, if you're the monarch, put a 5/5 red Dragon creature token with flying onto the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new CreateTokenEffect(new DragonToken2()), TargetController.YOU, false
                ), MonarchIsSourceControllerCondition.instance, "At the beginning of your upkeep, " +
                "if you're the monarch, create a 5/5 red Dragon creature token with flying."
        ));

    }

    private SkylineDespot(final SkylineDespot card) {
        super(card);
    }

    @Override
    public SkylineDespot copy() {
        return new SkylineDespot(this);
    }
}
