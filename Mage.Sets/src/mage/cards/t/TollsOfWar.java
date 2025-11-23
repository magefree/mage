package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AllyToken;
import mage.game.permanent.token.ClueArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TollsOfWar extends CardImpl {

    public TollsOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{B}");

        // When this enchantment enters, create a Clue token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ClueArtifactToken())));

        // Whenever you sacrifice a permanent during your turn, create a 1/1 white Ally creature token. This ability triggers only once each turn.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new CreateTokenEffect(new AllyToken()), StaticFilters.FILTER_PERMANENT
        ).withTriggerCondition(MyTurnCondition.instance).setTriggersLimitEachTurn(1));
    }

    private TollsOfWar(final TollsOfWar card) {
        super(card);
    }

    @Override
    public TollsOfWar copy() {
        return new TollsOfWar(this);
    }
}
