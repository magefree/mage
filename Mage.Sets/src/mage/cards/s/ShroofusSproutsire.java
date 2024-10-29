package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author Grath
 */
public final class ShroofusSproutsire extends CardImpl {
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.SAPROLING, "Saproling");

    public ShroofusSproutsire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAPROLING);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a Saproling you control deals combat damage to a player, create that many 1/1 green Saproling creature tokens.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new CreateTokenEffect(new SaprolingToken(), SavedDamageValue.MANY),
                filter, false, SetTargetPointer.NONE, true
        ));
    }

    private ShroofusSproutsire(final ShroofusSproutsire card) {
        super(card);
    }

    @Override
    public ShroofusSproutsire copy() {
        return new ShroofusSproutsire(this);
    }
}
