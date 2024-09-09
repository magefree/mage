package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author Grath
 */
public final class AdewaleBreakerOfChains extends CardImpl {
    private static final FilterCard filter = new FilterCard("an Assassin, Pirate, or Vehicle card");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("Vehicle you control");

    static {
        filter.add(Predicates.or(
                SubType.ASSASSIN.getPredicate(),
                SubType.PIRATE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        filter2.add(SubType.VEHICLE.getPredicate());
    }

    public AdewaleBreakerOfChains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When Adewale enters the battlefield, reveal the top six cards of your library. Put an Assassin, Pirate, or Vehicle card from among them into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new RevealLibraryPickControllerEffect(
                        6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM, false
                ), false));

        // Whenever a Vehicle you control deals combat damage to a player, you may return Adewale from your graveyard to your hand.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(), filter2,
                true, SetTargetPointer.NONE, true, false));
    }

    private AdewaleBreakerOfChains(final AdewaleBreakerOfChains card) {
        super(card);
    }

    @Override
    public AdewaleBreakerOfChains copy() {
        return new AdewaleBreakerOfChains(this);
    }
}
