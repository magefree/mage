package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class DisorderInTheCourt extends CardImpl {

    public DisorderInTheCourt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{U}");

        // Exile X target creatures, then investigate X times. Return the exiled cards to the battlefield tapped under their owners' control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new DisorderInTheCourtEffect());
        this.getSpellAbility().setTargetAdjuster(DisorderInTheCourtAdjuster.instance);
    }

    private DisorderInTheCourt(final DisorderInTheCourt card) {
        super(card);
    }

    @Override
    public DisorderInTheCourt copy() {
        return new DisorderInTheCourt(this);
    }
}

enum DisorderInTheCourtAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}

class DisorderInTheCourtEffect extends OneShotEffect {

    DisorderInTheCourtEffect() {
        super(Outcome.Benefit);
        staticText = "Exile X target creatures, then investigate X times. Return the exiled cards to the battlefield tapped under their owners' control at the beginning of the next end step";
    }

    private DisorderInTheCourtEffect(final DisorderInTheCourtEffect effect) {
        super(effect);
    }

    @Override
    public DisorderInTheCourtEffect copy() {
        return new DisorderInTheCourtEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> toExile = getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (!toExile.isEmpty()) {
            controller.moveCardsToExile(toExile, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
            game.getState().processAction(game);
        }
        new InvestigateEffect(ManacostVariableValue.REGULAR).apply(game, source);
        if (!toExile.isEmpty()) {
            Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(true, true);
            effect.setTargetPointer(new FixedTargets(new CardsImpl(toExile), game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        }
        return true;
    }

}
