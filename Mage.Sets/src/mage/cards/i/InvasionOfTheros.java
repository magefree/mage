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
    private static final FilterPermanent epharaFilter = new FilterControlledEnchantmentPermanent("another enchantment");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.GOD.getPredicate(),
                SubType.DEMIGOD.getPredicate()
        ));
        epharaFilter.add(AnotherPredicate.instance);
    }

    static {
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            epharaFilter, ComparisonType.MORE_THAN, 2, true
    );
    private static final Hint epharaHint = new ValueHint(
            "Other enchantments you control", new PermanentsOnBattlefieldCount(epharaFilter)
    );

    public InvasionOfTheros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{W}",
                "Ephara, Ever-Sheltering",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.GOD}, "WU"
        );

        // Invasion of Theros
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Theros enters the battlefield, search your library for an Aura, God, or Demigod card, reveal it, put it into your hand, then shuffle.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // Ephara, Ever-Sheltering
        this.getRightHalfCard().setPT(4, 4);

        // Ephara, Ever-Sheltering has lifelink and indestructible as long as you control at least three other enchantments.
        Ability staticAbility = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()),
                condition, "{this} has lifelink"
        ));
        staticAbility.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance()), condition,
                "and indestructible as long as you control at least three other enchantments"
        ));
        this.getRightHalfCard().addAbility(staticAbility.addHint(epharaHint));

        // Whenever another enchantment you control enters, draw a card.
        this.getRightHalfCard().addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), epharaFilter
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
