package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsChoicePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class Preacher extends CardImpl {

    public Preacher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may choose not to untap Preacher during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {T}: Gain control of target creature of an opponent's choice that they control for as long as Preacher remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreacherEffect(), new TapSourceCost());
        ability.addTarget(new TargetOpponentsChoicePermanent(1, 1, new FilterControlledCreaturePermanent(), false));
        this.addAbility(ability);

    }

    private Preacher(final Preacher card) {
        super(card);
    }

    @Override
    public Preacher copy() {
        return new Preacher(this);
    }
}

class PreacherEffect extends OneShotEffect {

    public PreacherEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of target creature of an opponent's choice that they control for as long as {this} remains tapped";
    }

    public PreacherEffect(final PreacherEffect effect) {
        super(effect);
    }

    @Override
    public PreacherEffect copy() {
        return new PreacherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && sourcePermanent != null && targetPermanent != null) {
            SourceTappedCondition sourceTappedCondition = SourceTappedCondition.TAPPED;
            ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                    new GainControlTargetEffect(Duration.Custom),
                    sourceTappedCondition,
                    "Gain control of target creature of an opponent's choice that they control for as long as {this} remains tapped");
            effect.setTargetPointer(new FixedTarget(targetPermanent.getId(), game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
