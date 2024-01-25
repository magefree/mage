package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;

/**
 * Case of the Locked Hothouse {3}{G}
 * Enchantment - Case
 * You may play an additional land on each of your turns.
 * To solve -- You control seven or more lands.
 * Solved -- You may look at the top card of your library any time, and you may play lands and cast creature and enchantment spells from the top of your library.
 *
 * @author DominionSpy
 */
public final class CaseOfTheLockedHothouse extends CardImpl {

    private static final FilterCard filter = new FilterCard("play lands and cast creature and enchantment spells");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public CaseOfTheLockedHothouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.CASE);

        // You may play an additional land on each of your turns.
        Ability initialAbility = new SimpleStaticAbility(new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield));
        // To solve -- You control seven or more lands.
        Condition toSolveCondition = new PermanentsOnTheBattlefieldCondition(
                new FilterLandPermanent("You control seven or more lands"),
                ComparisonType.OR_GREATER, 7, true);
        // Solved -- You may look at the top card of your library any time, and you may play lands and cast creature and enchantment spells from the top of your library.
        Ability solvedAbility = new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect());
        solvedAbility.addEffect(new PlayTheTopCardEffect(TargetController.YOU, filter, false)
                .setText(", and you may play lands and cast creature and enchantment spells from the top of your library."));

        CaseAbility caseAbility = new CaseAbility(initialAbility, toSolveCondition, solvedAbility);
        this.addAbility(caseAbility);
    }

    private CaseOfTheLockedHothouse(final CaseOfTheLockedHothouse card) {
        super(card);
    }

    @Override
    public CaseOfTheLockedHothouse copy() {
        return new CaseOfTheLockedHothouse(this);
    }
}
