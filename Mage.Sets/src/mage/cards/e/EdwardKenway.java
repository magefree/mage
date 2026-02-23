package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author Grath
 */
public final class EdwardKenway extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("tapped Assassin, Pirate, and/or Vehicle you control");
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.VEHICLE);

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(Predicates.or(
                SubType.ASSASSIN.getPredicate(),
                SubType.PIRATE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Tapped Assassins, Pirates, and Vehicles you control", xValue);

    public EdwardKenway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your end step, create a Treasure token for each tapped Assassin, Pirate, and/or Vehicle you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new TreasureToken(), xValue)).addHint(hint));

        // Whenever a Vehicle you control deals combat damage to a player, look at the top card of that player's library, then exile it face down. You may play that card for as long as it remains exiled.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.NONE)
                        .setText("look at the top card of that player's library, then exile it face down. "
                                + "You may play that card for as long as it remains exiled"), filter2,
                false, SetTargetPointer.PLAYER, true, true
        ));
    }

    private EdwardKenway(final EdwardKenway card) {
        super(card);
    }

    @Override
    public EdwardKenway copy() {
        return new EdwardKenway(this);
    }
}
