package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamizObscuraOculus extends CardImpl {

    public KamizObscuraOculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CEPHALID, SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you attack, target attacking creature can't be blocked this turn. It connives. Then choose another attacking creature with lesser power. That creature gains double strike until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new CantBeBlockedTargetEffect(), 1);
        ability.addEffect(new KamizConniveEffect());
        ability.addEffect(new KamizDoubleStrikeEffect().concatBy("Then"));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private KamizObscuraOculus(final KamizObscuraOculus card) {
        super(card);
    }

    @Override
    public KamizObscuraOculus copy() {
        return new KamizObscuraOculus(this);
    }
}

class KamizConniveEffect extends OneShotEffect {

    KamizConniveEffect() {
        super(Outcome.Benefit);
        staticText = "it connives";
    }

    private KamizConniveEffect(final KamizConniveEffect effect) {
        super(effect);
    }

    @Override
    public KamizConniveEffect copy() {
        return new KamizConniveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        return ConniveSourceEffect.connive(permanent, 1, source, game);
    }
}

class KamizDoubleStrikeEffect extends OneShotEffect {

    KamizDoubleStrikeEffect() {
        super(Outcome.AddAbility);
        staticText = "choose another attacking creature with lesser power. " +
                "That creature gains double strike until end of turn";
    }

    private KamizDoubleStrikeEffect(final KamizDoubleStrikeEffect effect) {
        super(effect);
    }

    @Override
    public KamizDoubleStrikeEffect copy() {
        return new KamizDoubleStrikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        FilterPermanent filter = new FilterAttackingCreature("another attacking creature with lesser power");
        filter.add(Predicates.not(new PermanentIdPredicate(permanent.getId())));
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, permanent.getPower().getValue()));
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (target.choose(outcome, source.getControllerId(), source.getSourceId(), source, game)) {
            game.addEffect(
                    new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance())
                    .setTargetPointer(new FixedTarget(target.getFirstTarget(), game))
                    , source);
        }
        return true;
    }
}
