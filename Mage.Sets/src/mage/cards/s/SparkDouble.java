package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
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
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)));
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
                + "except it enters with an additional +1/+1 counter on it if it’s a creature, "
                + "it enters with an additional loyalty counter on it if it’s a planeswalker, and it isn’t legendary if that permanent is legendary.");
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
        Permanent destCard = game.getPermanentEntering(copyToObjectId);
        if (destCard == null) {
            return false;
        }

        // it isn’t legendary if that permanent is legendary
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

        // enters with an additional +1/+1 counter on it if it’s a creature
        if (copyFromBlueprint.isCreature()) {
            destCard.addCounters(CounterType.P1P1.createInstance(), source, game);
        }

        // enters with an additional loyalty counter on it if it’s a planeswalker
        if (copyFromBlueprint.isPlaneswalker()) {
            destCard.addCounters(CounterType.LOYALTY.createInstance(), source, game);
        }

        return true;
    }

}
