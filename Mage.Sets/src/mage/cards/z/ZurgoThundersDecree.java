package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.CantBeSacrificedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MobilizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZurgoThundersDecree extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.WARRIOR, "");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final Condition condition = new IsStepCondition(PhaseStep.END_TURN, true);

    public ZurgoThundersDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Mobilize 2
        this.addAbility(new MobilizeAbility(2));

        // During your end step, Warrior tokens you control have "This token can't be sacrificed."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(new GainAbilityControlledEffect(
                new SimpleStaticAbility(new CantBeSacrificedSourceEffect()),
                Duration.WhileOnBattlefield, filter
        ), condition, "during your end step, Warrior tokens you control have \"This token can't be sacrificed.\"")));
    }

    private ZurgoThundersDecree(final ZurgoThundersDecree card) {
        super(card);
    }

    @Override
    public ZurgoThundersDecree copy() {
        return new ZurgoThundersDecree(this);
    }
}
