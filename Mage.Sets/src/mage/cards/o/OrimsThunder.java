package mage.cards.o;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class OrimsThunder extends CardImpl {

    public OrimsThunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));

        // Destroy target artifact or enchantment. If Orim's Thunder was kicked, it deals damage equal to that permanent's converted mana cost to target creature.
        this.getSpellAbility().addEffect(new OrimsThunderEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new OrimsThunderEffect2(),
                KickedCondition.ONCE,
                "If this spell was kicked, it deals damage equal to that permanent's mana value to target creature")
        );
        this.getSpellAbility().setTargetAdjuster(OrimsThunderAdjuster.instance);
    }

    private OrimsThunder(final OrimsThunder card) {
        super(card);
    }

    @Override
    public OrimsThunder copy() {
        return new OrimsThunder(this);
    }
}

enum OrimsThunderAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetCreaturePermanent());
        }
    }

}

class OrimsThunderEffect2 extends OneShotEffect {

    OrimsThunderEffect2() {
        super(Outcome.Damage);
    }

    OrimsThunderEffect2(final OrimsThunderEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        MageObject firstTarget = game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        Permanent secondTarget = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (firstTarget != null) {
            damage = firstTarget.getManaValue();
        }
        boolean kicked = KickedCondition.ONCE.apply(game, source);
        if (kicked && secondTarget != null) {
            secondTarget.damage(damage, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public OrimsThunderEffect2 copy() {
        return new OrimsThunderEffect2(this);
    }
}

class OrimsThunderEffect extends OneShotEffect {

    OrimsThunderEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target artifact or enchantment";
    }

    OrimsThunderEffect(final OrimsThunderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            return target.destroy(source, game, false);
        }
        return false;
    }

    @Override
    public OrimsThunderEffect copy() {
        return new OrimsThunderEffect(this);
    }
}