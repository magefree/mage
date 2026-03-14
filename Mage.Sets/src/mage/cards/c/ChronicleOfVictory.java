package mage.cards.c;

import java.util.UUID;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllOfChosenSubtypeEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ChronicleOfVictory extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");
    private static final FilterSpell filterSpell = new FilterSpell("a spell of the chosen type");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filterSpell.add(ChosenSubtypePredicate.TRUE);
    }

    public ChronicleOfVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);

        // As Chronicle of Victory enters, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +2/+2 and have first strike and trample.
        SimpleStaticAbility buffAbility = new SimpleStaticAbility(new BoostAllOfChosenSubtypeEffect(
                2, 2, Duration.WhileOnBattlefield, filter, false
        ).setText("creatures you control of the chosen type get +2/+2"));
        buffAbility.addEffect(
            new GainAbilityAllOfChosenSubtypeEffect(
                FirstStrikeAbility.getInstance(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED
            ).setText("and have first strike")
        );
        buffAbility.addEffect(
            new GainAbilityAllOfChosenSubtypeEffect(
                TrampleAbility.getInstance(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED
            ).setText("and trample")
        );
        this.addAbility(buffAbility);

        // Whenever you cast a spell of the chosen type, draw a card.
        this.addAbility(
            new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filterSpell, false
            )
        );
    }

    private ChronicleOfVictory(final ChronicleOfVictory card) {
        super(card);
    }

    @Override
    public ChronicleOfVictory copy() {
        return new ChronicleOfVictory(this);
    }
}
