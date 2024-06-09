
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class BorborygmosEnraged extends CardImpl {

    public BorborygmosEnraged(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{G}{G}");
        this.subtype.add(SubType.CYCLOPS);

        this.supertype.add(SuperType.LEGENDARY);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        //Trample
        this.addAbility(TrampleAbility.getInstance());

        //Whenever Borborygmous Enraged deals combat damage to a player, reveal the top three cards of your library. Put all land cards revealed this way into your hand and the rest into your graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new RevealLibraryPutIntoHandEffect(3, StaticFilters.FILTER_CARD_LANDS, Zone.GRAVEYARD), false, false));

        //Discard a land card: Borborygmos Enraged deals 3 damage to any target
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3), new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND_A)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BorborygmosEnraged(final BorborygmosEnraged card) {
        super(card);
    }

    @Override
    public BorborygmosEnraged copy() {
        return new BorborygmosEnraged(this);
    }
}
