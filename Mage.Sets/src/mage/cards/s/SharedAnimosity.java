package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SharedAnimosity extends CardImpl {

    public SharedAnimosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever a creature you control attacks, it gets +1/+0 until end of turn for each other attacking creature that shares a creature type with it.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new SharedAnimosityEffect(), false, true)
        );
    }

    public SharedAnimosity(final SharedAnimosity card) {
        super(card);
    }

    @Override
    public SharedAnimosity copy() {
        return new SharedAnimosity(this);
    }
}

class SharedAnimosityEffect extends ContinuousEffectImpl {

    private int power;

    public SharedAnimosityEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);

    }

    public SharedAnimosityEffect(final SharedAnimosityEffect effect) {
        super(effect);
        this.power = effect.power;
    }

    @Override
    public SharedAnimosityEffect copy() {
        return new SharedAnimosityEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Permanent permanent = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (permanent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(Predicates.not(new PermanentIdPredicate(this.targetPointer.getFirst(game, source))));
            filter.add(AttackingPredicate.instance);
            filter.add(new SharedAnimosityPredicate(permanent));
            power = game.getBattlefield().count(filter, source.getControllerId(), source.getSourceId(), game);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (target != null) {
            target.addPower(power);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "it gets +1/+0 until end of turn for each other attacking creature that shares a creature type with it";
    }
}

class SharedAnimosityPredicate implements Predicate<Card> {

    private final Permanent permanent;

    SharedAnimosityPredicate(Permanent permanent) {
        this.permanent = permanent;
    }

    @Override
    public boolean apply(Card input, Game game) {
        return permanent != null && input != null && permanent.shareCreatureTypes(input, game);
    }
}
