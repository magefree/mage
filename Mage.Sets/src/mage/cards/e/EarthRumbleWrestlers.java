package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.LandfallCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.watchers.common.LandfallWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthRumbleWrestlers extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    private static final Condition condition = new CompoundCondition(
            "you control a land creature or a land entered the battlefield under your control this turn",
            new PermanentsOnTheBattlefieldCondition(filter), LandfallCondition.instance
    );
    private static final Hint hint = new ConditionHint(condition);

    public EarthRumbleWrestlers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R/G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.PERFORMER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // This creature gets +1/+0 and has trample as long as you control a land creature or a land entered the battlefield under your control this turn.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                condition, "{this} gets +1/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                condition, "and has trample as long as you control a land creature " +
                "or a land entered the battlefield under your control this turn"
        ));
        this.addAbility(ability.addHint(hint), new LandfallWatcher());
    }

    private EarthRumbleWrestlers(final EarthRumbleWrestlers card) {
        super(card);
    }

    @Override
    public EarthRumbleWrestlers copy() {
        return new EarthRumbleWrestlers(this);
    }
}
