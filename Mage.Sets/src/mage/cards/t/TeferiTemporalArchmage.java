package mage.cards.t;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.game.command.emblems.TeferiTemporalArchmageEmblem;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class TeferiTemporalArchmage extends CardImpl {

    public TeferiTemporalArchmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);

        this.setStartingLoyalty(5);

        // +1: Look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.BOTTOM_ANY), 1));

        // -1: Untap up to four target permanents.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new UntapTargetEffect(), -1);
        loyaltyAbility.addTarget(new TargetPermanent(0, 4, new FilterPermanent(), false));
        this.addAbility(loyaltyAbility);

        // -10: You get an emblem with "You may activate loyalty abilities of planeswalkers you control on any player's turn any time you could cast an instant."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TeferiTemporalArchmageEmblem()), -10));

        // Teferi, Temporal Archmage can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());

    }

    private TeferiTemporalArchmage(final TeferiTemporalArchmage card) {
        super(card);
    }

    @Override
    public TeferiTemporalArchmage copy() {
        return new TeferiTemporalArchmage(this);
    }
}
