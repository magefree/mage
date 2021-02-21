
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author fireshoes
 */
public final class Deadshot extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public Deadshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Tap target creature.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        TargetCreaturePermanent target = new TargetCreaturePermanent();
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        // It deals damage equal to its power to another target creature.
        this.getSpellAbility().addEffect(new DeadshotDamageEffect());
        target = new TargetCreaturePermanent(filter);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);
    }

    private Deadshot(final Deadshot card) {
        super(card);
    }

    @Override
    public Deadshot copy() {
        return new Deadshot(this);
    }
}

class DeadshotDamageEffect extends OneShotEffect {

    public DeadshotDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature deals damage equal to its power to another target creature";
    }

    public DeadshotDamageEffect(final DeadshotDamageEffect effect) {
        super(effect);
        this.setTargetPointer(new SecondTargetPointer());
    }

    @Override
    public DeadshotDamageEffect copy() {
        return new DeadshotDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent ownCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (ownCreature != null) {
            int damage = ownCreature.getPower().getValue();
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                targetCreature.damage(damage, ownCreature.getId(), source, game, false, true);
                return true;
            }
        }
        return false;
    }
}
