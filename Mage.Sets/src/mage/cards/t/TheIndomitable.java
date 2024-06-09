package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author jam736
 */
public final class TheIndomitable extends CardImpl {

    public TheIndomitable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Crew 3
        this.addAbility(new CrewAbility(3));

        // Whenever a creature you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.NONE, true
        ));

        // You may cast The Indomitable from your graveyard as long as you control three or more tapped Pirates and/or Vehicles.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new TheIndomitableCastEffect()));
    }

    private TheIndomitable(final TheIndomitable card) {
        super(card);
    }

    @Override
    public TheIndomitable copy() {
        return new TheIndomitable(this);
    }
}

class TheIndomitableCastEffect extends AsThoughEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("three or more tapped pirates and/or vehicles");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(Predicates.or(
                SubType.PIRATE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    TheIndomitableCastEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard as long as you control three or more tapped Pirates and/or Vehicles.";
    }

    private TheIndomitableCastEffect(final TheIndomitableCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TheIndomitableCastEffect copy() {
        return new TheIndomitableCastEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null
                    && card.isOwnedBy(affectedControllerId)
                    && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                    && game.getBattlefield().count(filter, source.getControllerId(), source, game) >= 3) {
                return true;
            }
        }
        return false;
    }
}
