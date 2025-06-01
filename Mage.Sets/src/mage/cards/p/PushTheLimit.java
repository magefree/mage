package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.VehiclesBecomeArtifactCreatureEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Jmlundeen
 */
public final class PushTheLimit extends CardImpl {

    private static final FilterPermanent filterCreatures = new FilterControlledPermanent("Creatures you control");

    public PushTheLimit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Return all Mount and Vehicle cards from your graveyard to the battlefield. Sacrifice them at the beginning of the next end step.
        this.getSpellAbility().addEffect(new PushTheLimitEffect());

        // Vehicles you control become artifact creatures until end of turn. Creatures you control gain haste until end of turn.
        this.getSpellAbility().addEffect(new VehiclesBecomeArtifactCreatureEffect(Duration.EndOfTurn)
                .concatBy("<br>"));
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filterCreatures));
    }

    private PushTheLimit(final PushTheLimit card) {
        super(card);
    }

    @Override
    public PushTheLimit copy() {
        return new PushTheLimit(this);
    }
}

class PushTheLimitEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Mount and Vehicle cards");
    static {
        filter.add(Predicates.or(
                SubType.MOUNT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    PushTheLimitEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return all " + filter.getMessage() + " from your graveyard to the battlefield. " +
                "Sacrifice them at the beginning of the next end step.";
    }

    private PushTheLimitEffect(final PushTheLimitEffect effect) {
        super(effect);
    }

    @Override
    public PushTheLimitEffect copy() {
        return new PushTheLimitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cards = controller.getGraveyard().getCards(filter, source.getControllerId(), source, game);
        boolean result = controller.moveCards(cards, Zone.BATTLEFIELD, source, game,
                false, false, false, null);
        if (result) {
            List<Permanent> permanentsToSac = cards.stream()
                    .map(card -> CardUtil.getPermanentFromCardPutToBattlefield(card, game))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            Effect sacrificeEffect = new SacrificeTargetEffect("sacrifice them", source.getControllerId());
            sacrificeEffect.setTargetPointer(new FixedTargets(permanentsToSac, game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
        }
        return result;
    }
}
