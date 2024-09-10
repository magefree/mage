package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KaheeraTheOrphanguard extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.CAT.getPredicate(),
                SubType.ELEMENTAL.getPredicate(),
                SubType.NIGHTMARE.getPredicate(),
                SubType.DINOSAUR.getPredicate(),
                SubType.BEAST.getPredicate()
        ));
    }

    public KaheeraTheOrphanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/W}{G/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Companion — Each creature card in your starting deck is a Cat, Elemental, Nightmare, Dinosaur or Beast card.
        this.addAbility(new CompanionAbility(KaheeraTheOrphanguardCompanionCondition.instance));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Each other creature you control that's a Cat, Elemental, Nightmare, Dinosaur, or Beast gets +1/+1 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        ).setText("Each other creature you control that's a Cat, Elemental, Nightmare, Dinosaur, or Beast gets +1/+1"));
        ability.addEffect(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter, "and has vigilance"
        ));
        this.addAbility(ability);
    }

    private KaheeraTheOrphanguard(final KaheeraTheOrphanguard card) {
        super(card);
    }

    @Override
    public KaheeraTheOrphanguard copy() {
        return new KaheeraTheOrphanguard(this);
    }
}

enum KaheeraTheOrphanguardCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Each creature card in your starting deck is a Cat, Elemental, Nightmare, Dinosaur, or Beast card.";
    }

    private static final List<SubType> subtypes = Arrays.asList(
            SubType.CAT,
            SubType.ELEMENTAL,
            SubType.NIGHTMARE,
            SubType.DINOSAUR,
            SubType.BEAST
    );

    private static boolean isCardLegal(Card card) {
        return subtypes.stream().anyMatch(card::hasSubTypeForDeckbuilding);
    }

    @Override
    public boolean isLegal(Set<Card> deck, int minimumDeckSize) {
        return deck.stream()
                .filter(card -> card.hasCardTypeForDeckbuilding(CardType.CREATURE))
                .allMatch(KaheeraTheOrphanguardCompanionCondition::isCardLegal);
    }
}
