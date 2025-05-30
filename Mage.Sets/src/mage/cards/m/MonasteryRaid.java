package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonasteryRaid extends CardImpl {

    public MonasteryRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Freerunning {X}{R}
        this.addAbility(new FreerunningAbility("{X}{R}"));

        // Exile the top two cards of your library. If this spell's freerunning cost was paid, exile the top X cards of your library instead. You may play the exiled cards until the end of your next turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ExileTopXMayPlayUntilEffect(GetXValue.instance, false, Duration.UntilEndOfYourNextTurn),
                new ExileTopXMayPlayUntilEffect(2, Duration.UntilEndOfYourNextTurn),
                MonasteryRaidCondition.instance, "exile the top two cards of your library. " +
                "If this spell's freerunning cost was paid, exile the top X cards of your library instead. " +
                "You may play the exiled cards until the end of your next turn"
        ));
    }

    private MonasteryRaid(final MonasteryRaid card) {
        super(card);
    }

    @Override
    public MonasteryRaid copy() {
        return new MonasteryRaid(this);
    }
}

enum MonasteryRaidCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, FreerunningAbility.getActivationKey());
    }
}
