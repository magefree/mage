package mage.cards.c;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.abilities.Ability;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.hint.common.CaseSolvedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class CaseOfTheLostWitness extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("You control a legendary Homunculus");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(SubType.HOMUNCULUS.getPredicate());
    }

    public CaseOfTheLostWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.CASE);

        // When this Case enters, conjure four cards named Fblthp, the Lost into your library, then shuffle. Draw a card.
        Ability initialAbility = new EntersBattlefieldTriggeredAbility(new ConjureCardEffect("Fblthp, the Lost", Zone.LIBRARY, 4));
        initialAbility.addEffect(new ShuffleLibrarySourceEffect().setText(", then shuffle."));
        initialAbility.addEffect(new DrawCardSourceControllerEffect(1));

        // To solve -- You control a legendary Homunculus.
        Condition toSolveCondition = new PermanentsOnTheBattlefieldCondition(
            filter, ComparisonType.OR_GREATER, 1, true
        );

        // Solved -- You may look at the top card of your library any time and you may play lands and cast spells from the top of your library.
        Ability solvedAbility = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new LookAtTopCardOfLibraryAnyTimeEffect(), SolvedSourceCondition.SOLVED, ""
        ));
        solvedAbility.addEffect(new ConditionalAsThoughEffect(
            new PlayFromTopOfLibraryEffect(),
            SolvedSourceCondition.SOLVED
        ).setText(", and you may play lands and cast spells from the top of your library."));

        this.addAbility(new CaseAbility(initialAbility, toSolveCondition, solvedAbility)
            .addHint(new CaseOfTheLostWitnessHint(toSolveCondition)
        ));
    }

    private CaseOfTheLostWitness(final CaseOfTheLostWitness card) {
        super(card);
    }

    @Override
    public CaseOfTheLostWitness copy() {
        return new CaseOfTheLostWitness(this);
    }
}

class CaseOfTheLostWitnessHint extends CaseSolvedHint {

    private static final FilterPermanent filter = new FilterControlledPermanent("You control a legendary Homunculus");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(SubType.HOMUNCULUS.getPredicate());
    }

    CaseOfTheLostWitnessHint(Condition condition) {
        super(condition);
    }

    private CaseOfTheLostWitnessHint(final CaseOfTheLostWitnessHint hint) {
        super(hint);
    }

    @Override
    public CaseOfTheLostWitnessHint copy() {
        return new CaseOfTheLostWitnessHint(this);
    }

    @Override
    public String getConditionText(Game game, Ability ability) {
        int homunculus = game.getBattlefield().count(filter, ability.getControllerId(), ability, game);
        return "Legendary Homunculus: " + homunculus + " (need 1).";
    }
}
