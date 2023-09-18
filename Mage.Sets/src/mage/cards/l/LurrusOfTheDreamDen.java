package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.CastFromGraveyardOnceStaticAbility;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LurrusOfTheDreamDen extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public LurrusOfTheDreamDen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Companion — Each permanent card in your starting deck has converted mana cost 2 or less.
        this.addAbility(new CompanionAbility(LurrusOfTheDreamDenCompanionCondition.instance));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // During each of your turns, you may cast one permanent spell with converted mana cost 2 or less from your graveyard.
        this.addAbility(new CastFromGraveyardOnceStaticAbility(filter, "During each of your turns, you may cast one permanent spell with mana value 2 or less from your graveyard"));
    }

    private LurrusOfTheDreamDen(final LurrusOfTheDreamDen card) {
        super(card);
    }

    @Override
    public LurrusOfTheDreamDen copy() {
        return new LurrusOfTheDreamDen(this);
    }
}

enum LurrusOfTheDreamDenCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Each permanent card in your starting deck has mana value 2 or less.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int minimumDeckSize) {
        return deck.stream()
                .filter(MageObject::isPermanent)
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0) <= 2;
    }
}
