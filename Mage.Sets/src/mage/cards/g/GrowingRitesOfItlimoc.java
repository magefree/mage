package mage.cards.g;

import mage.Mana;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public final class GrowingRitesOfItlimoc extends TransformingDoubleFacedCard {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledCreaturePermanent("if you control four or more creatures"), ComparisonType.MORE_THAN, 3
    );
    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED);

    public GrowingRitesOfItlimoc(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{2}{G}",
                "Itlimoc, Cradle of the Sun",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // When Growing Rites of Itlimoc enters the battlefield, look at the top four cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_ANY
        )));

        // At the beginning of your end step, if you control four or more creatures, transform Growing Rites of Itlimoc.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                new TransformSourceEffect(), TargetController.YOU, condition, false
        ));

        // Itlimoc, Cradle of the Sun
        // (Transforms from Growing Rites of Itlimoc.)
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("<i>(Transforms from Growing Rites of Itlimoc.)</i>")));

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());

        // {T}: Add {G} for each creature you control.
        this.getRightHalfCard().addAbility(new DynamicManaAbility(Mana.GreenMana(1), xValue));
    }

    private GrowingRitesOfItlimoc(final GrowingRitesOfItlimoc card) {
        super(card);
    }

    @Override
    public GrowingRitesOfItlimoc copy() {
        return new GrowingRitesOfItlimoc(this);
    }
}
