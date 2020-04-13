package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

import java.util.Set;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class KerugaTheMacrosage extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("other permanent you control with converted mana cost 3 or greater");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new ConvertedManaCostPredicate(ComparisonType.MORE_THAN, 2));
    }

    public KerugaTheMacrosage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G/U}{G/U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.HIPPO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Companion — Your starting deck contains only cards with converted mana cost 3 or greater and land cards.
        this.addAbility(new CompanionAbility(KerugaCondition.instance));
        // When Keruga, the Macrosage enters the battlefield, draw a card for each other permanent you control with converted mana cost 3 or greater.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter))));
    }

    private KerugaTheMacrosage(final KerugaTheMacrosage card) {
        super(card);
    }

    @Override
    public KerugaTheMacrosage copy() {
        return new KerugaTheMacrosage(this);
    }
}

enum KerugaCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Your starting deck contains only cards with converted mana cost 3 or greater and land cards.";
    }

    @Override
    public boolean isLegal(Set<Card> deck) {
        return deck.stream().allMatch(card -> card.isLand() || card.getConvertedManaCost() >= 3);
    }
}