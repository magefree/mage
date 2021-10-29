package mage.cards.w;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WallOfStolenIdentity extends CardImpl {

    final static private String rule = "You may have Wall of Stolen Identity enter the battlefield as a copy of any "
            + "creature on the battlefield, except it's a wall in addition to its other types and it has defender. "
            + "When you do, tap the copied creature and it doesn't untap during its "
            + "controller's untap step for as long as you control {this}";

    public WallOfStolenIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Wall of Stolen Identity enter the battlefield as a copy of any creature on the battlefield, except it's a wall in addition to its other types and it has defender. When you do, tap the copied creature and it doesn't untap during its controller's untap step for as long as you control Wall of Stolen Identity.
        Ability ability = new SimpleStaticAbility(
                new EntersBattlefieldEffect(new WallOfStolenIdentityCopyEffect(), rule, true)
        );
        this.addAbility(ability);
    }

    private WallOfStolenIdentity(final WallOfStolenIdentity card) {
        super(card);
    }

    @Override
    public WallOfStolenIdentity copy() {
        return new WallOfStolenIdentity(this);
    }
}

class WallOfStolenIdentityCopyEffect extends OneShotEffect {

    private static final String rule2 = "When you do, tap the copied creature and it doesn't untap during its "
            + "controller's untap step for as long as you control {this}.";

    public WallOfStolenIdentityCopyEffect() {
        super(Outcome.Copy);
        staticText = rule2;
    }

    public WallOfStolenIdentityCopyEffect(final WallOfStolenIdentityCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        final Permanent sourcePermanent = permanent;
        if (controller == null
                || sourcePermanent == null) {
            return false;
        }
        Target target = new TargetPermanent(new FilterCreaturePermanent("target creature (you copy from)"));
        target.setRequired(true);
        if (source instanceof SimpleStaticAbility) {
            target = new TargetPermanent(new FilterCreaturePermanent("creature (you copy from)"));
            target.setRequired(false);
            target.setNotTarget(true);
        }
        if (!target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
            return false;
        }
        controller.choose(Outcome.Copy, target, source.getSourceId(), game);
        Permanent copyFromPermanent = game.getPermanent(target.getFirstTarget());
        if (copyFromPermanent == null) {
            return false;
        }
        game.copyPermanent(copyFromPermanent, sourcePermanent.getId(), source, new CopyApplier() {
            @Override
            public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
                blueprint.addSubType(SubType.WALL);
                blueprint.getAbilities().add(DefenderAbility.getInstance());
                return true;
            }
        });

        copyFromPermanent.tap(source, game);
        // Incredibly, you can't just add a fixed target to a continuousrulemodifyingeffect, thus the workaround.
        ContinuousRuleModifyingEffect effect = new DontUntapInControllersUntapStepSourceEffect();
        Ability ability = new SimpleStaticAbility(effect);
        ContinuousEffect effect2 = new GainAbilityTargetEffect(ability, Duration.Custom);
        ConditionalContinuousEffect conditionalEffect = new ConditionalContinuousEffect(
                effect2, new WallOfStolenIdentityCondition(
                        source,
                        source.getControllerId(),
                        sourcePermanent.getZoneChangeCounter(game)), "");
        conditionalEffect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
        game.addEffect(conditionalEffect, source);
        return true;
    }

    @Override
    public WallOfStolenIdentityCopyEffect copy() {
        return new WallOfStolenIdentityCopyEffect(this);
    }
}

class WallOfStolenIdentityCondition implements Condition {

    // Checks for when it leaves play or changes control
    private final Ability ability;
    private final UUID controllerId;
    private final int zcc;

    public WallOfStolenIdentityCondition(Ability ability, UUID controllerId, int zcc) {
        this.ability = ability;
        this.controllerId = controllerId;
        this.zcc = zcc;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanentSource = game.getPermanent(ability.getSourceId());
        if (permanentSource != null) {
            return permanentSource.getZoneChangeCounter(game) == zcc + 1
                    && permanentSource.getControllerId() == controllerId;
        }
        return false;
    }
}
