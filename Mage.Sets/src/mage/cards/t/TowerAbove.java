package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class TowerAbove extends CardImpl {

    public TowerAbove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2/G}{2/G}{2/G}");

        // <i>({2G} can be paid with any two mana or with {G}. This card's converted mana cost is 6.)</i>
        // Until end of turn, target creature gets +4/+4 and gains trample, wither, and "When this creature attacks, target creature blocks it this turn if able."
        this.getSpellAbility().addEffect(new TowerAboveEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private TowerAbove(final TowerAbove card) {
        super(card);
    }

    @Override
    public TowerAbove copy() {
        return new TowerAbove(this);
    }
}

class TowerAboveEffect extends OneShotEffect {

    public TowerAboveEffect() {
        super(Outcome.BoostCreature);
        staticText = "Until end of turn, target creature gets +4/+4 and gains trample, wither, and \"When this creature attacks, target creature blocks it this turn if able.\"";
    }

    public TowerAboveEffect(final TowerAboveEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }

        ContinuousEffect effect = new BoostTargetEffect(4, 4, Duration.EndOfTurn);
        ContinuousEffect effect2 = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        ContinuousEffect effect3 = new GainAbilityTargetEffect(WitherAbility.getInstance(), Duration.EndOfTurn);
        ContinuousEffect effect4 = new GainAbilityTargetEffect(new TowerAboveTriggeredAbility(), Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(target.getId(), game));
        effect2.setTargetPointer(new FixedTarget(target.getId(), game));
        effect3.setTargetPointer(new FixedTarget(target.getId(), game));
        effect4.setTargetPointer(new FixedTarget(target.getId(), game));
        effect4.setText("");
        game.addEffect(effect, source);
        game.addEffect(effect2, source);
        game.addEffect(effect3, source);
        game.addEffect(effect4, source);
        return true;
    }

    @Override
    public TowerAboveEffect copy() {
        return new TowerAboveEffect(this);
    }
}

class TowerAboveTriggeredAbility extends TriggeredAbilityImpl {

    public TowerAboveTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(Duration.EndOfTurn), false);
    }

    public TowerAboveTriggeredAbility(final TowerAboveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            this.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent();
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature attacks, target creature blocks it this turn if able.";
    }

    @Override
    public TowerAboveTriggeredAbility copy() {
        return new TowerAboveTriggeredAbility(this);
    }
}
