
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class FlamerushRider extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(AttackingPredicate.instance);
    }

    public FlamerushRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Flamerush Rider attacks, create a token tapped and attacking that's a copy of another target attacking creature. Exile the token at end of combat.
        Ability ability = new AttacksTriggeredAbility(new FlamerushRiderEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Dash {2}{R}{R}
        this.addAbility(new DashAbility("{2}{R}{R}"));
    }

    private FlamerushRider(final FlamerushRider card) {
        super(card);
    }

    @Override
    public FlamerushRider copy() {
        return new FlamerushRider(this);
    }
}

class FlamerushRiderEffect extends OneShotEffect {

    public FlamerushRiderEffect() {
        super(Outcome.Copy);
        this.staticText = "create a token tapped and attacking that's a copy of another target attacking creature. Exile the token at end of combat";
    }

    public FlamerushRiderEffect(final FlamerushRiderEffect effect) {
        super(effect);
    }

    @Override
    public FlamerushRiderEffect copy() {
        return new FlamerushRiderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true, 1, true, true);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.apply(game, source);
            for (Permanent addedToken : effect.getAddedPermanents()) {
                Effect exileEffect = new ExileTargetEffect();
                exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect), false).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
