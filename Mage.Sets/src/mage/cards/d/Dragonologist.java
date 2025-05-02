package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Dragonologist extends CardImpl {

    private static final FilterCard filter = new FilterCard("an instant, sorcery, or Dragon card");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.DRAGON, "untapped Dragons");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                SubType.DRAGON.getPredicate()
        ));
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public Dragonologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When this creature enters, look at the top six cards of your library. You may reveal an instant, sorcery, or Dragon card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));

        // Untapped Dragons you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        )));
    }

    private Dragonologist(final Dragonologist card) {
        super(card);
    }

    @Override
    public Dragonologist copy() {
        return new Dragonologist(this);
    }
}
