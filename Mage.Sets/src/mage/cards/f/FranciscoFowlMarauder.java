package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FranciscoFowlMarauder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.PIRATE, "Pirates");

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
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new ExploreSourceEffect(false, "{this}"), filter
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
