package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MacabreMockery extends CardImpl {

    public MacabreMockery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{R}");

        // Put target creature card from an opponent's graveyard onto the battlefield under your control. It gets +2/+0 and gains haste until end of turn. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new MacabreMockeryEffect());
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(StaticFilters.FILTER_CARD_CREATURE));
    }

    private MacabreMockery(final MacabreMockery card) {
        super(card);
    }

    @Override
    public MacabreMockery copy() {
        return new MacabreMockery(this);
    }
}

class MacabreMockeryEffect extends OneShotEffect {

    MacabreMockeryEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put target creature card from an opponent's graveyard onto the battlefield under your control. " +
                "It gets +2/+0 and gains haste until end of turn. Sacrifice it at the beginning of the next end step.";
    }

    private MacabreMockeryEffect(final MacabreMockeryEffect effect) {
        super(effect);
    }

    @Override
    public MacabreMockeryEffect copy() {
        return new MacabreMockeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        Effect sacrificeEffect = new SacrificeTargetEffect("sacrifice " + permanent.getLogName(), controller.getId());
        sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
        return true;
    }
}
