package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TargetPlayerGainControlSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Grath
 */
public final class YesManPersonalSecuritron extends CardImpl {

    public YesManPersonalSecuritron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Target opponent gains control of Yes Man, Personal Securitron. When they do, you draw two cards and put a quest counter on Yes Man. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new YesManPersonalSecuritronControlEffect(), new TapSourceCost(), MyTurnCondition.instance);
        ability.addTarget(new TargetOpponent());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);

        // Wild Card -- When Yes Man leaves the battlefield, its owner creates a tapped 1/1 white Soldier creature token for each quest counter on it.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new YesManPersonalSecuritronLeavesEffect(), false));
    }

    private YesManPersonalSecuritron(final YesManPersonalSecuritron card) {
        super(card);
    }

    @Override
    public YesManPersonalSecuritron copy() {
        return new YesManPersonalSecuritron(this);
    }
}

class YesManPersonalSecuritronControlEffect extends TargetPlayerGainControlSourceEffect {
    YesManPersonalSecuritronControlEffect() {
        super();
        this.staticText = "Target opponent gains control of {this}. When they do, you draw two cards and put a quest counter on Yes Man.";
    }

    private YesManPersonalSecuritronControlEffect(final YesManPersonalSecuritronControlEffect effect) {
        super(effect);
    }

    @Override
    public YesManPersonalSecuritronControlEffect copy() {
        return new YesManPersonalSecuritronControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean controlChanged = super.apply(game, source);
        if (!controlChanged) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DrawCardSourceControllerEffect(2), false, "When they do, you draw two cards and put a quest counter on Yes Man.");
        OneShotEffect counterEffect = new AddCountersTargetEffect(CounterType.QUEST.createInstance());
        counterEffect.setTargetPointer(new FixedTarget(source.getSourceId()));
        ability.addEffect(counterEffect);
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class YesManPersonalSecuritronLeavesEffect extends CreateTokenTargetEffect {
    YesManPersonalSecuritronLeavesEffect() {
        super(new SoldierToken(), new CountersSourceCount(CounterType.QUEST), true);
        this.staticText = "its owner creates a tapped 1/1 white Soldier creature token for each quest counter on it.";
    }

    private YesManPersonalSecuritronLeavesEffect(final YesManPersonalSecuritronLeavesEffect effect) {
        super(effect);
    }

    @Override
    public YesManPersonalSecuritronLeavesEffect copy() {
        return new YesManPersonalSecuritronLeavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent yesman = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (yesman == null) {
            return false;
        }
        this.setTargetPointer(new FixedTarget(yesman.getOwnerId()));
        return super.apply(game, source);
    }
}