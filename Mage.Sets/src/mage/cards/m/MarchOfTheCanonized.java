package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.token.IxalanVampireToken;
import mage.game.permanent.token.VampireDemonToken;

import java.util.UUID;

/**
 * @author arcox
 */
public final class MarchOfTheCanonized extends CardImpl {

    public MarchOfTheCanonized(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{W}{W}");

        // When March of the Canonized enters the battlefield, create X 1/1 white Vampire creature tokens with lifelink.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new IxalanVampireToken(), GetXValue.instance)));

        // At the beginning of your upkeep, if your devotion to white and black is seven or greater, create a 4/3 white and black Vampire Demon creature token with flying.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new VampireDemonToken()))
                .withInterveningIf(MarchOfTheCanonizedCondition.instance).addHint(DevotionCount.WB.getHint()));
    }

    private MarchOfTheCanonized(final MarchOfTheCanonized card) {
        super(card);
    }

    @Override
    public MarchOfTheCanonized copy() {
        return new MarchOfTheCanonized(this);
    }
}

enum MarchOfTheCanonizedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return DevotionCount.WB.calculate(game, source, null) >= 7;
    }

    @Override
    public String toString() {
        return "your devotion to white and black is seven or greater";
    }
}
