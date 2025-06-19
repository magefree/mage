package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of your upkeep, if you're the monarch, put a 5/5 red Dragon creature token with flying onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new DragonToken2()))
                .withInterveningIf(MonarchIsSourceControllerCondition.instance));
    }

    private SkylineDespot(final SkylineDespot card) {
        super(card);
    }

    @Override
    public SkylineDespot copy() {
        return new SkylineDespot(this);
    }
}
