package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValleyRotcaller extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.SQUIRREL.getPredicate(),
                SubType.BAT.getPredicate(),
                SubType.LIZARD.getPredicate(),
                SubType.RAT.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Other Squirrels, Bats, Lizards, and Rats you control", xValue);

    public ValleyRotcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Valley Rotcaller attacks, each opponent loses X life and you gain X life, where X is the number of other Squirrels, Bats, Lizards, and Rats you control.
        Ability ability = new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(xValue)
                .setText("each opponent loses X life"));
        ability.addEffect(new GainLifeEffect(
                xValue, "and you gain X life, where X is the number " +
                "of other Squirrels, Bats, Lizards, and Rats you control"
        ));
        this.addAbility(ability);
    }

    private ValleyRotcaller(final ValleyRotcaller card) {
        super(card);
    }

    @Override
    public ValleyRotcaller copy() {
        return new ValleyRotcaller(this);
    }
}
