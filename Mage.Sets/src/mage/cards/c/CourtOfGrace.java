package mage.cards.c;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.AngelToken;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CourtOfGrace extends CardImpl {

    public CourtOfGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // When Court of Grace enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of your upkeep, create a 1/1 white Spirit creature token with flying. If you're the monarch, create a 4/4 white Angel creature token with flying instead.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConditionalOneShotEffect(
                new CreateTokenEffect(new AngelToken()), new CreateTokenEffect(new SpiritWhiteToken()),
                MonarchIsSourceControllerCondition.instance, "create a 1/1 white Spirit creature token with flying. " +
                "If you're the monarch, create a 4/4 white Angel creature token with flying instead"
        ), TargetController.YOU, false));
    }

    private CourtOfGrace(final CourtOfGrace card) {
        super(card);
    }

    @Override
    public CourtOfGrace copy() {
        return new CourtOfGrace(this);
    }
}
