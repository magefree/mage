package mage.cards.f;

import mage.MageInt;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfIsolation extends CardImpl {

    public FearOfIsolation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As an additional cost to cast this spell, return a permanent you control to its owner's hand.
        this.getSpellAbility().addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent()));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private FearOfIsolation(final FearOfIsolation card) {
        super(card);
    }

    @Override
    public FearOfIsolation copy() {
        return new FearOfIsolation(this);
    }
}
