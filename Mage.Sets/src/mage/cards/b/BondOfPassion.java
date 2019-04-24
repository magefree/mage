package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BondOfPassion extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent();
    private static final FilterCreaturePlayerOrPlaneswalker filter2
            = new FilterCreaturePlayerOrPlaneswalker("any other target");

    static {
        filter.add(new AnotherTargetPredicate(1));
        filter2.getCreatureFilter().add(new AnotherTargetPredicate(2));
        filter2.getPlaneswalkerFilter().add(new AnotherTargetPredicate(2));
        filter2.getPlayerFilter().add(new AnotherTargetPredicate(2));
    }

    public BondOfPassion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. Bond of Passion deals 2 damage to any other target.
        this.getSpellAbility().addEffect(new BondOfPassionEffect());
        Target target = new TargetPermanent(filter);
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);
        target = new TargetAnyTarget(filter2);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);
    }

    private BondOfPassion(final BondOfPassion card) {
        super(card);
    }

    @Override
    public BondOfPassion copy() {
        return new BondOfPassion(this);
    }
}

class BondOfPassionEffect extends OneShotEffect {

    BondOfPassionEffect() {
        super(Outcome.Benefit);
        staticText = "Gain control of target creature until end of turn. Untap that creature. " +
                "It gains haste until end of turn. {this} deals 2 damage to any other target.";
    }

    private BondOfPassionEffect(final BondOfPassionEffect effect) {
        super(effect);
    }

    @Override
    public BondOfPassionEffect copy() {
        return new BondOfPassionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
            permanent.untap(game);
            effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        Permanent permanent2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent2 != null) {
            permanent2.damage(2, source.getSourceId(), game);
            return true;
        }
        Player player = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (player != null) {
            player.damage(2, source.getSourceId(), game);
        }
        return true;
    }
}
