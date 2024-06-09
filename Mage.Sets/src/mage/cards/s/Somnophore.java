
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.SourceRemainsInZoneCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Somnophore extends CardImpl {

    public Somnophore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Somnophore deals damage to a player, tap target creature that player controls. That creature doesn't untap during its controller's untap step for as long as Somnophore remains on the battlefield.
        ContinuousRuleModifyingEffect skipUntapEffect = new DontUntapInControllersUntapStepTargetEffect(Duration.WhileOnBattlefield);
        skipUntapEffect.setText("That creature doesn't untap during its controller's untap step for as long as {this} remains on the battlefield");
        ConditionalContinuousRuleModifyingEffect effect = new ConditionalContinuousRuleModifyingEffect(skipUntapEffect, new SourceRemainsInZoneCondition(Zone.BATTLEFIELD));
        Ability ability = new SomnophoreTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Somnophore(final Somnophore card) {
        super(card);
    }

    @Override
    public Somnophore copy() {
        return new Somnophore(this);
    }
}

class SomnophoreTriggeredAbility extends TriggeredAbilityImpl {

    public SomnophoreTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        this.addTarget(new TargetCreaturePermanent());
    }

    private SomnophoreTriggeredAbility(final SomnophoreTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SomnophoreTriggeredAbility copy() {
        return new SomnophoreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
                filter.add(new ControllerIdPredicate(opponent.getId()));

                this.getTargets().clear();
                this.addTarget(new TargetCreaturePermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to a player, "
                + "tap target creature that player controls. "
                + "That creature doesn't untap during its controller's untap step "
                + "for as long as {this} remains on the battlefield.";
    }
}
