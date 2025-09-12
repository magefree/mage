package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.PlayLandOrCastSpellTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Controllable;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheEndstone extends CardImpl {

    public TheEndstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever you play a land or cast a spell, draw a card.
        this.addAbility(new PlayLandOrCastSpellTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // At the beginning of your end step, your life total becomes half your starting life total, rounded up.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new TheEndstoneEffect()));
    }

    private TheEndstone(final TheEndstone card) {
        super(card);
    }

    @Override
    public TheEndstone copy() {
        return new TheEndstone(this);
    }
}

class TheEndstoneEffect extends OneShotEffect {

    TheEndstoneEffect() {
        super(Outcome.Benefit);
        staticText = "your life total becomes half your starting life total, rounded up";
    }

    private TheEndstoneEffect(final TheEndstoneEffect effect) {
        super(effect);
    }

    @Override
    public TheEndstoneEffect copy() {
        return new TheEndstoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .ifPresent(player -> player.setLife(game.getStartingLife() / 2 + game.getStartingLife() % 2, game, source));
        return true;
    }
}
