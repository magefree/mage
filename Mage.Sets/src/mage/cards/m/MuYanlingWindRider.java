package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.VehicleToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MuYanlingWindRider extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.VEHICLE, "Vehicles");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("creatures you control with flying");

    static {
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    public MuYanlingWindRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When this creature enters, create a 3/2 colorless Vehicle artifact token with crew 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new VehicleToken())));

        // Vehicles you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever one or more creatures you control with flying deal combat damage to a player, draw a card.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter2
        ));
    }

    private MuYanlingWindRider(final MuYanlingWindRider card) {
        super(card);
    }

    @Override
    public MuYanlingWindRider copy() {
        return new MuYanlingWindRider(this);
    }
}
