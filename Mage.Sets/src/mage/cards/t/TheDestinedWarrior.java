package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDestinedWarrior extends CardImpl {

    private static final FilterCard filter = new FilterCard("Cleric, Rogue, Warrior, and Wizard spells");

    static {
        filter.add(Predicates.or(
                SubType.CLERIC.getPredicate(),
                SubType.ROGUE.getPredicate(),
                SubType.WARRIOR.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
    }

    public TheDestinedWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Cleric, Rogue, Warrior, and Wizard spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // At the beginning of combat on your turn, creatures you control get +1/+0 until end of turn. If you have a full party, creatures you control get +3/+0 until end of turn instead.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostControlledEffect(3, 0, Duration.EndOfTurn)),
                new AddContinuousEffectToGame(new BoostControlledEffect(1, 0, Duration.EndOfTurn)),
                FullPartyCondition.instance, "creatures you control get +1/+0 until end of turn. " +
                "If you have a full party, creatures you control get +3/+0 until end of turn instead"
        )).addHint(PartyCountHint.instance));
    }

    private TheDestinedWarrior(final TheDestinedWarrior card) {
        super(card);
    }

    @Override
    public TheDestinedWarrior copy() {
        return new TheDestinedWarrior(this);
    }
}
