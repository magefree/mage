package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.OneOrMoreDealDamageTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FranciscoFowlMarauder extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.PIRATE, "Pirates");

    public FranciscoFowlMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Francisco, Fowl Marauder can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever one or more Pirates you control deal damage to a player, Francisco explores.
        this.addAbility(new OneOrMoreDealDamageTriggeredAbility(
                new ExploreSourceEffect(false, "{this}"),
                filter, false, true
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private FranciscoFowlMarauder(final FranciscoFowlMarauder card) {
        super(card);
    }

    @Override
    public FranciscoFowlMarauder copy() {
        return new FranciscoFowlMarauder(this);
    }
}
