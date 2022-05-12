package mage.cards.j;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class JaceTheLivingGuildpact extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("another target nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public JaceTheLivingGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.setStartingLoyalty(5);

        // +1: Look at the top two cards of your library. Put one of them into your graveyard.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(2, 1, PutCards.GRAVEYARD, PutCards.TOP_ANY), 1));

        // -3: Return another target nonland permanent to its owner's hand.
        LoyaltyAbility ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -3);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -8: Each player shuffles their hand and graveyard into their library. You draw seven cards.
        ability = new LoyaltyAbility(new ShuffleHandGraveyardAllEffect(), -8);
        ability.addEffect(new DrawCardSourceControllerEffect(7, "you"));
        this.addAbility(ability);
    }

    private JaceTheLivingGuildpact(final JaceTheLivingGuildpact card) {
        super(card);
    }

    @Override
    public JaceTheLivingGuildpact copy() {
        return new JaceTheLivingGuildpact(this);
    }
}
