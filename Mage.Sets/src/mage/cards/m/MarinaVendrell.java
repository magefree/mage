package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.LockOrUnlockRoomTargetEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarinaVendrell extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ROOM);

    public MarinaVendrell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Marina Vendrell enters, reveal the top seven cards of your library. Put all enchantment cards from among them into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealLibraryPutIntoHandEffect(
                7, StaticFilters.FILTER_CARD_ENCHANTMENTS, Zone.LIBRARY, false
        )));

        // {T}: Lock or unlock a door of target Room you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new LockOrUnlockRoomTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MarinaVendrell(final MarinaVendrell card) {
        super(card);
    }

    @Override
    public MarinaVendrell copy() {
        return new MarinaVendrell(this);
    }
}
