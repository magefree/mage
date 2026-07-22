package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CadetToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class AjaniUnrelenting extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature except for tokens you control");

    static {
        filter.add(AjaniUnrelentingPredicate.instance);
    }

    public AjaniUnrelenting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.setStartingLoyalty(5);

        // Whenever you activate a loyalty ability, create a 2/2 colorless Wizard Soldier creature token named Cadet.
        this.addAbility(new ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility(
            new CreateTokenEffect(new CadetToken()), SetTargetPointer.NONE
        ));

        // +1: Creatures you control get +1/+0 and gain haste until end of turn.
        Ability ability = new LoyaltyAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn), 1);
        ability.addEffect(
            new GainAbilityControlledEffect(
                HasteAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
            ).setText("and gain haste until end of turn")
        );
        this.addAbility(ability);

        // -2: Discard your hand, then draw a card for each creature you control.
        Ability ability2 = new LoyaltyAbility(new DiscardHandControllerEffect(), -2);
        ability2.addEffect(new DrawCardSourceControllerEffect(
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE)
        ).concatBy(", then"));
        this.addAbility(ability2);

        // -3: Ajani deals 4 damage to each creature except for tokens you control.
        this.addAbility(new LoyaltyAbility(new DamageAllEffect(4, filter), -3));
    }

    private AjaniUnrelenting(final AjaniUnrelenting card) {
        super(card);
    }

    @Override
    public AjaniUnrelenting copy() {
        return new AjaniUnrelenting(this);
    }
}

enum AjaniUnrelentingPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return !input.getObject().isToken() || !input.getObject().isControlledBy(input.getPlayerId());
    }
}
