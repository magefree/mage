package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.PirateRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaringPiracy extends CardImpl {

    public DaringPiracy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of your combat on your turn, create a 1/1 red Pirate creature token with menace and haste. Exile it at the beginning of the next end step.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DaringPiracyEffect(), TargetController.YOU, false
        ));
    }

    private DaringPiracy(final DaringPiracy card) {
        super(card);
    }

    @Override
    public DaringPiracy copy() {
        return new DaringPiracy(this);
    }
}

class DaringPiracyEffect extends OneShotEffect {

    DaringPiracyEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 red Pirate creature token with menace and haste. " +
                "Exile it at the beginning of the next end step";
    }

    private DaringPiracyEffect(final DaringPiracyEffect effect) {
        super(effect);
    }

    @Override
    public DaringPiracyEffect copy() {
        return new DaringPiracyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new PirateRedToken());
        effect.apply(game, source);
        effect.exileTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
