
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetControlledPermanent;

/**
 * @author jpgunter
 */
public final class MultaniYavimayasAvatar extends CardImpl {

    private static final FilterControlledLandPermanent LANDS_YOU_CONTROL_FILTER = new FilterControlledLandPermanent("lands you control");
    private static final FilterLandCard LAND_FILTER = new FilterLandCard();

    public MultaniYavimayasAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(ReachAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());

        //Multani, Yavimaya's Avatar gets +1/+1 for each land you control and each land in your graveyard.
        CardsInControllerGraveyardCount graveyardCount = new CardsInControllerGraveyardCount(LAND_FILTER);
        PermanentsOnBattlefieldCount permanentsOnBattlefieldCount = new PermanentsOnBattlefieldCount(LANDS_YOU_CONTROL_FILTER);

        DynamicValue powerToughnessValue = new AdditiveDynamicValue(graveyardCount, permanentsOnBattlefieldCount);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(powerToughnessValue, powerToughnessValue, Duration.WhileOnBattlefield)
                        .setText("{this} gets +1/+1 for each land you control and each land card in your graveyard")
        ));

        //{1}{G}, Return two lands you control to their owner's hand: Return Multani from your graveyard to your hand.
        SimpleActivatedAbility returnToHand = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{1}{G}"));
        returnToHand.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(2, 2, LANDS_YOU_CONTROL_FILTER, true)));
        this.addAbility(returnToHand);
    }

    private MultaniYavimayasAvatar(final MultaniYavimayasAvatar card) {
        super(card);
    }

    @Override
    public MultaniYavimayasAvatar copy() {
        return new MultaniYavimayasAvatar(this);
    }
}
