
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.TurnFaceUpTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class SkirkAlarmist extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("face-down creature you control");

    static {
        filter.add(FaceDownPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SkirkAlarmist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {tap}: Turn target face-down creature you control face up. At the beginning of the next end step, sacrifice it.
        Ability ability = new SimpleActivatedAbility(new TurnFaceUpTargetEffect(), new TapSourceCost());
        ability.addEffect(new SkirkAlarmistEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private SkirkAlarmist(final SkirkAlarmist card) {
        super(card);
    }

    @Override
    public SkirkAlarmist copy() {
        return new SkirkAlarmist(this);
    }
}

class SkirkAlarmistEffect extends OneShotEffect {

    public SkirkAlarmistEffect() {
        super(Outcome.Sacrifice);
        staticText = "At the beginning of the next end step, sacrifice it";
    }

    private SkirkAlarmistEffect(final SkirkAlarmistEffect effect) {
        super(effect);
    }

    @Override
    public SkirkAlarmistEffect copy() {
        return new SkirkAlarmistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice this", source.getControllerId());
            sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}
