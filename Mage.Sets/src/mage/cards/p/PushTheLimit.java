package mage.cards.p;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.common.continuous.CreaturesBecomeOtherTypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.VehiclesBecomeArtifactCreatureEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

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

class PushTheLimitSacrificeEffect extends OneShotEffect {

    private final Set<Card> cards;

    public PushTheLimitSacrificeEffect(Set<Card> cards) {
        super(Outcome.Sacrifice);
        this.cards = cards;
    }

    public PushTheLimitSacrificeEffect(final PushTheLimitSacrificeEffect effect) {
        super(effect);
        this.cards = effect.cards;
    }

    @Override
    public PushTheLimitSacrificeEffect copy() {
        return new PushTheLimitSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> permanents = cards.stream()
                .map(Card::getId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (permanents.isEmpty()) {
            return false;
        }
        permanents.forEach(permanent -> {
            permanent.sacrifice(source, game);
        });
        return true;
    }
}

class PushTheLimitEffect extends ReturnFromYourGraveyardToBattlefieldAllEffect {
    private static final FilterCard filter = new FilterCard("Mount and Vehicle cards");
    static {
        filter.add(Predicates.or(
                SubType.MOUNT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }
    public PushTheLimitEffect() {
        super(filter);
        staticText += ". Sacrifice them at the beginning of the next end step.";
    }

    public PushTheLimitEffect(final PushTheLimitEffect effect) {
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
        boolean result = controller.moveCards(controller.getGraveyard().getCards(filter, source.getControllerId(), source, game),
                Zone.BATTLEFIELD, source, game, false, false, false, null);
        if (result) {

            Effect sacrificeEffect = new PushTheLimitSacrificeEffect(cards);
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
        }
        return result;
    }
}
