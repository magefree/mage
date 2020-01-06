package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class SparkDouble extends CardImpl {

    private static FilterPermanent filter = new FilterControlledPermanent("a creature or planeswalker you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
    }

    public SparkDouble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Spark Double enter the battlefield as a copy of a creature or planeswalker you control,
        // except it enters with an additional +1/+1 counter on it if it’s a creature,
        // it enters with an additional loyalty counter on it if it’s a planeswalker, and it isn’t legendary if that permanent is legendary.
        Effect effect = new CopyPermanentEffect(filter, new SparkDoubleExceptEffectsApplyerToPermanent());
        effect.setText("as a copy of a creature or planeswalker you control, "
                + "except it enters with an additional +1/+1 counter on it if it's a creature, "
                + "it enters with an additional loyalty counter on it if it's a planeswalker, and it isn't legendary if that permanent is legendary.");
        EntersBattlefieldAbility ability = new EntersBattlefieldAbility(effect, true);
        this.addAbility(ability);
    }

    public SparkDouble(final SparkDouble card) {
        super(card);
    }

    @Override
    public SparkDouble copy() {
        return new SparkDouble(this);
    }
}

class SparkDoubleExceptEffectsApplyerToPermanent extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, Permanent copyFromBlueprint, Ability source, UUID copyToObjectId) {
        return apply(game, (MageObject) copyFromBlueprint, source, copyToObjectId);
    }

    @Override
    public boolean apply(Game game, MageObject copyFromBlueprint, Ability source, UUID copyToObjectId) {
        // copyToObjectId can be new token outside from game, don't use it

        // it isn’t legendary if that permanent is legendary
        //
        // Spark Double isn’t legendary if it copies a legendary permanent, and this exception is copiable. If something
        // else copies Spark Double later, that copy also won’t be legendary. If you control two or more permanents
        // with the same name but only one is legendary, the “legend rule” doesn’t apply.
        // (2019-05-03)
        //
        // So, it's must make changes in blueprint (for farther copyable)
        copyFromBlueprint.getSuperType().remove(SuperType.LEGENDARY);

        // TODO: Blood Moon problem, can't apply on type changing effects (same as TeferisTimeTwist)
        // see https://magic.wizards.com/en/articles/archive/feature/war-spark-release-notes-2019-04-19
        // If the copied permanent is affected by a type-changing effect, Spark Double may enter the battlefield with
        // different permanent types than the copied permanent currently has. Use the characteristics of Spark Double as
        // it enters the battlefield, not of the copied permanent, to determine whether it enters with an additional
        // counter on it. Notably, if Spark Double copies a Gideon planeswalker that's a creature because its loyalty
        // ability caused it to become a planeswalker creature, Spark Double enters as a noncreature planeswalker and
        // doesn't get a +1/+1 counter. On the other hand, if Spark Double copies Gideon Blackblade during your turn,
        // Spark Double enters as a planeswalker creature and gets both kinds of counters.

        // counters only for original card, not copies
        if (!isCopyOfCopy(source, copyToObjectId)) {
            // enters with an additional +1/+1 counter on it if it’s a creature
            if (copyFromBlueprint.isCreature()) {
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), false).apply(game, source);
            }

            // enters with an additional loyalty counter on it if it’s a planeswalker
            if (copyFromBlueprint.isPlaneswalker()) {
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(), false).apply(game, source);
            }
        }

        return true;
    }

}
