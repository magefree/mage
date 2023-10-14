package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class NogiDracoZealot extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();
    private static final FilterCard filter2 = new FilterCard("Dragon spells");

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filter2.add(SubType.DRAGON.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2);

    private static final Hint hint
            = new ValueHint("Dragons you control", new PermanentsOnBattlefieldCount(filter));

    public NogiDracoZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOBOLD, SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Dragon spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter2, 1)));

        // Whenever Nogi, Draco-Zealot attacks, if you control three or more Dragons, until end
        // of turn, Nogi becomes a Dragon with base power and toughness 5/5 and gains flying.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new BecomesCreatureSourceEffect(
                        new CreatureToken(5, 5, "Dragon with base power and toughness 5/5 and gains flying")
                                .withSubType(SubType.DRAGON)
                                .withAbility(FlyingAbility.getInstance()),
                        CardType.CREATURE, Duration.EndOfTurn), false
                ), condition, "Whenever {this} attacks, if you control three or more Dragons, until end of turn, " +
                "{this} becomes a Dragon with base power and toughness 5/5 and gains flying"
        );
        this.addAbility(ability.addHint(hint));
    }

    private NogiDracoZealot(final NogiDracoZealot card) {
        super(card);
    }

    @Override
    public NogiDracoZealot copy() {
        return new NogiDracoZealot(this);
    }
}
