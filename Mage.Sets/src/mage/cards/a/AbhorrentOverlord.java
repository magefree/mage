package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HarpyToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AbhorrentOverlord extends CardImpl {

    public AbhorrentOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Abhorrent Overlord enters the battlefield, create a number of 1/1 black Harpy creature tokens with flying equal to your devotion to black.
        Effect effect = new CreateTokenEffect(new HarpyToken(), DevotionCount.B);
        effect.setText("create a number of 1/1 black Harpy creature tokens with flying equal to your devotion to black. <i>(Each {B} in the mana costs of permanents you control counts toward your devotion to black.)</i>");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect).addHint(DevotionCount.B.getHint()));

        // At the beginning of your upkeep, sacrifice a creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, null), TargetController.YOU, false));
    }

    private AbhorrentOverlord(final AbhorrentOverlord card) {
        super(card);
    }

    @Override
    public AbhorrentOverlord copy() {
        return new AbhorrentOverlord(this);
    }
}
