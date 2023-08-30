package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfTheros extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("an Aura, God, or Demigod card");

    private static final FilterPermanent filter2 = new FilterControlledEnchantmentPermanent("another enchantment");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.GOD.getPredicate(),
                SubType.DEMIGOD.getPredicate()
        ));
        filter2.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            filter2, ComparisonType.MORE_THAN, 2, true
    );
    private static final Hint hint = new ValueHint(
            "Other enchantments you control", new PermanentsOnBattlefieldCount(filter2)
    );

    public InvasionOfTheros(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{W}",
                "Ephara, Ever-Sheltering",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.GOD}, "UW"
        );
        this.getLeftHalfCard().setStartingDefense(4);
        this.getRightHalfCard().setPT(4, 4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Theros enters the battlefield, search your library for an Aura, God, or Demigod card, reveal it, put it into your hand, then shuffle.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // Ephara, Ever-Sheltering
        // Ephara, Ever-Sheltering has lifelink and indestructible as long as you control at least three other enchantments.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()),
                condition, "{this} has lifelink"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance()), condition,
                "and indestructible as long as you control at least three other enchantments"
        ));
        this.getRightHalfCard().addAbility(ability.addHint(hint));

        // Whenever another enchantment enters the battlefield under your control, draw a card.
        this.getRightHalfCard().addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter2
        ));
    }

    private InvasionOfTheros(final InvasionOfTheros card) {
        super(card);
    }

    @Override
    public InvasionOfTheros copy() {
        return new InvasionOfTheros(this);
    }
}
