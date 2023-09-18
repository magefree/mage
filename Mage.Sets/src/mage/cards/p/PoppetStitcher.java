package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieDecayedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PoppetStitcher extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CREATURE_TOKEN, ComparisonType.MORE_THAN, 2
    );
    private static final Hint hint = new ValueHint(
            "Creature tokens you control", new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CREATURE_TOKEN)
    );

    public PoppetStitcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.p.PoppetFactory.class;

        // Whenever you cast an instant or sorcery spell, create a 2/2 black Zombie creature token with decayed.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new ZombieDecayedToken()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // At the beginning of your upkeep, if you control three or more creature tokens, you may transform Poppet Sticher.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new TransformSourceEffect(),
                        TargetController.YOU, true
                ), condition, "At the beginning of your upkeep, " +
                "if you control three or more creature tokens, you may transform {this}."
        ).addHint(hint));
    }

    private PoppetStitcher(final PoppetStitcher card) {
        super(card);
    }

    @Override
    public PoppetStitcher copy() {
        return new PoppetStitcher(this);
    }
}
