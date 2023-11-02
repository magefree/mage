
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;

/**
 *
 * @author Styxo
 */
public final class SentryOak extends CardImpl {

    public SentryOak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // At the beginning of combat on your turn, you may clash with an opponent. If you win, Sentry Oak gets +2/+0 and loses defender until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoIfClashWonEffect(new SentryOakEffect()), TargetController.YOU, true));
    }

    private SentryOak(final SentryOak card) {
        super(card);
    }

    @Override
    public SentryOak copy() {
        return new SentryOak(this);
    }
}

class SentryOakEffect extends OneShotEffect {

    public SentryOakEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} gets +2/+0 and loses defender until end of turn";
    }

    private SentryOakEffect(final SentryOakEffect effect) {
        super(effect);
    }

    @Override
    public SentryOakEffect copy() {
        return new SentryOakEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getPermanent(source.getSourceId()) != null) {
            ContinuousEffect continuousEffect = new BoostSourceEffect(2, 0, Duration.EndOfTurn);
            game.addEffect(continuousEffect, source);

            continuousEffect = new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.EndOfTurn);
            game.addEffect(continuousEffect, source);
            return true;

        }
        return false;
    }
}
