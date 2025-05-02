package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrightglassGearhulk extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("artifact, creature, and/or enchantment cards with mana value 1 or less");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public BrightglassGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{G}{G}{W}{W}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature enters, you may search your library for up to two artifact, creature, and/or enchantment cards with mana value 1 or less, reveal them, put them into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(0, 2, filter), true
        ), true));
    }

    private BrightglassGearhulk(final BrightglassGearhulk card) {
        super(card);
    }

    @Override
    public BrightglassGearhulk copy() {
        return new BrightglassGearhulk(this);
    }
}
