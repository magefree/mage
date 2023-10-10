
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.condition.common.SourceRemainsInZoneCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class SowerOfTemptation extends CardImpl {

    public SowerOfTemptation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Sower of Temptation enters the battlefield, gain control of target creature for as long as Sower of Temptation remains on the battlefield.
        // 10/1/2007: You retain control of the targeted creature as long as Sower of Temptation
        //            remains on the battlefield, even if a different player gains control of Sower of Temptation itself.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.Custom, true),
                SourceOnBattlefieldCondition.instance,
                "gain control of target creature for as long as {this} remains on the battlefield");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private SowerOfTemptation(final SowerOfTemptation card) {
        super(card);
    }

    @Override
    public SowerOfTemptation copy() {
        return new SowerOfTemptation(this);
    }
}

class SowerOfTemptationGainControlEffect extends OneShotEffect {

    public SowerOfTemptationGainControlEffect() {
        super(Outcome.GainControl);
        this.staticText = "gain control of target creature for as long as {this} remains on the battlefield";
    }

    private SowerOfTemptationGainControlEffect(final SowerOfTemptationGainControlEffect effect) {
        super(effect);
    }

    @Override
    public SowerOfTemptationGainControlEffect copy() {
        return new SowerOfTemptationGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.Custom),
                new SourceRemainsInZoneCondition(Zone.BATTLEFIELD),
                "gain control of target creature for as long as {this} remains on the battlefield");
        game.addEffect(effect, source);
        return false;
    }
}