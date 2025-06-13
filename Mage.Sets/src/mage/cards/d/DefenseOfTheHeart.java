package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class DefenseOfTheHeart extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterOpponentsCreaturePermanent("an opponent controls three or more creatures"),
            ComparisonType.MORE_THAN, 2
    );

    public DefenseOfTheHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // At the beginning of your upkeep, if an opponent controls three or more creatures, sacrifice Defense of the Heart, search your library for up to two creature cards, and put those cards onto the battlefield. Then shuffle your library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect()).withInterveningIf(condition);
        ability.addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                0, 2, StaticFilters.FILTER_CARD_CREATURES
        )).concatBy(","));
        this.addAbility(ability);
    }

    private DefenseOfTheHeart(final DefenseOfTheHeart card) {
        super(card);
    }

    @Override
    public DefenseOfTheHeart copy() {
        return new DefenseOfTheHeart(this);
    }
}
