package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author DominionSpy
 */
public final class CaseOfTheShatteredPact extends CardImpl {

    public CaseOfTheShatteredPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}");

        this.subtype.add(SubType.CASE);

        // When this Case enters the battlefield, search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        Ability initialAbility = new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true));
        // To solve -- There are five colors among permanents you control.
        // Solved -- At the beginning of combat on your turn, target creature you control gains flying, double strike, and vigilance until end of turn.
        Ability solvedAbility = new BeginningOfCombatTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("target creature you control gains flying")
        ).withTriggerCondition(SolvedSourceCondition.SOLVED);
        solvedAbility.addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance())
                .setText(", double strike,"));
        solvedAbility.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setText("and vigilance until end of turn."));
        solvedAbility.addTarget(new TargetControlledCreaturePermanent());

        this.addAbility(new CaseAbility(initialAbility, CaseOfTheShatteredPactCondition.instance, solvedAbility)
                .addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private CaseOfTheShatteredPact(final CaseOfTheShatteredPact card) {
        super(card);
    }

    @Override
    public CaseOfTheShatteredPact copy() {
        return new CaseOfTheShatteredPact(this);
    }
}

enum CaseOfTheShatteredPactCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.calculate(game, source, null) >= 5;
    }

    @Override
    public String toString() {
        return "There are five colors among permanents you control";
    }
}
