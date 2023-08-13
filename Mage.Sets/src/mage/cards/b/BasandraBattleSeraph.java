package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class BasandraBattleSeraph extends CardImpl {
    
    public BasandraBattleSeraph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Players can't cast spells during combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BasandraBattleSeraphEffect()));

        // {R}: Target creature attacks this turn if able.
        Effect effect = new AttacksIfAbleTargetEffect(Duration.EndOfTurn);
        effect.setOutcome(Outcome.Detriment);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
    }
    
    private BasandraBattleSeraph(final BasandraBattleSeraph card) {
        super(card);
    }
    
    @Override
    public BasandraBattleSeraph copy() {
        return new BasandraBattleSeraph(this);
    }
}

class BasandraBattleSeraphEffect extends ContinuousRuleModifyingEffectImpl {
    
    public BasandraBattleSeraphEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "Players can't cast spells during combat";
    }
    
    public BasandraBattleSeraphEffect(final BasandraBattleSeraphEffect effect) {
        super(effect);
    }
    
    @Override
    public BasandraBattleSeraphEffect copy() {
        return new BasandraBattleSeraphEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurnPhaseType() == TurnPhase.COMBAT) {
            return true;
        }
        return false;
    }
}
