package mage.cards.l;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LeechriddenSwamp extends CardImpl {

    private static final FilterPermanent filter =
            new FilterControlledPermanent("you control two or more black permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);

    public LeechriddenSwamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.subtype.add(SubType.SWAMP);

        // ({tap}: Add {B}.)
        this.addAbility(new BlackManaAbility());

        // Leechridden Swamp enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {B}, {tap}: Each opponent loses 1 life. Activate this ability only if you control two or more black permanents.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new LoseLifeOpponentsEffect(1), new ManaCostsImpl<>("{B}"), condition
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private LeechriddenSwamp(final LeechriddenSwamp card) {
        super(card);
    }

    @Override
    public LeechriddenSwamp copy() {
        return new LeechriddenSwamp(this);
    }
}
