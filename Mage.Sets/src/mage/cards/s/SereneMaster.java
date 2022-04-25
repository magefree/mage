package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class SereneMaster extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature it's blocking");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKED_BY);
    }

    public SereneMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Whenever Serene Master blocks, exchange its power and the power of target creature it's blocking until end of combat.
        Ability ability = new BlocksSourceTriggeredAbility(new SereneMasterEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private SereneMaster(final SereneMaster card) {
        super(card);
    }

    @Override
    public SereneMaster copy() {
        return new SereneMaster(this);
    }
}

class SereneMasterEffect extends OneShotEffect {

    public SereneMasterEffect() {
        super(Outcome.Benefit);
        this.staticText = "exchange its power and the power of target creature it's blocking until end of combat";
    }

    public SereneMasterEffect(final SereneMasterEffect effect) {
        super(effect);
    }

    @Override
    public SereneMasterEffect copy() {
        return new SereneMasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        Permanent attackingCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (sourceCreature != null && attackingCreature != null) {
            StaticValue newSourcePower = StaticValue.get(attackingCreature.getPower().getValue());
            StaticValue newAttackerPower = StaticValue.get(sourceCreature.getPower().getValue());
            ContinuousEffect effect = new SetPowerToughnessTargetEffect(newSourcePower, null, Duration.EndOfCombat);
            effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
            game.addEffect(effect, source);
            effect = new SetPowerToughnessTargetEffect(newAttackerPower, null, Duration.EndOfCombat);
            effect.setTargetPointer(new FixedTarget(attackingCreature.getId(), game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
