package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author dustinconrad
 */
public final class FootstepsOfTheGoryo extends CardImpl {

    public FootstepsOfTheGoryo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.subtype.add(SubType.ARCANE);

        // Return target creature card from your graveyard to the battlefield. Sacrifice that creature at the beginning of the next end step.
        this.getSpellAbility().addEffect(new FootstepsOfTheGoryoEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
    }

    private FootstepsOfTheGoryo(final FootstepsOfTheGoryo card) {
        super(card);
    }

    @Override
    public FootstepsOfTheGoryo copy() {
        return new FootstepsOfTheGoryo(this);
    }
}

class FootstepsOfTheGoryoEffect extends OneShotEffect {

    public FootstepsOfTheGoryoEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return target creature card from your graveyard to the battlefield. Sacrifice that creature at the beginning of the next end step";
    }

    private FootstepsOfTheGoryoEffect(final FootstepsOfTheGoryoEffect effect) {
        super(effect);
    }

    @Override
    public FootstepsOfTheGoryoEffect copy() {
        return new FootstepsOfTheGoryoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        // Sacrifice it at end of turn
                        Effect sacrificeEffect = new SacrificeTargetEffect("Sacrifice that creature at the beginning of next end step", source.getControllerId());
                        sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
