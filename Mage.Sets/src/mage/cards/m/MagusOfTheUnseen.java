
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author emerald000
 */
public final class MagusOfTheUnseen extends CardImpl {
    
    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact an opponent controls");
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public MagusOfTheUnseen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{U}, {tap}: Untap target artifact an opponent controls and gain control of it until end of turn. It gains haste until end of turn. When you lose control of the artifact, tap it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filter));
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("and gain control of it until end of turn. ");
        ability.addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("It gains haste until end of turn. ");
        ability.addEffect(effect);
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new MagusOfTheUnseenDelayedTriggeredAbility(), true));
        this.addAbility(ability);
    }

    private MagusOfTheUnseen(final MagusOfTheUnseen card) {
        super(card);
    }

    @Override
    public MagusOfTheUnseen copy() {
        return new MagusOfTheUnseen(this);
    }
}

class MagusOfTheUnseenDelayedTriggeredAbility extends DelayedTriggeredAbility {

    MagusOfTheUnseenDelayedTriggeredAbility() {
        super(new TapTargetEffect(), Duration.EndOfGame, true); // effect can last over turns end, if you still control the target but only one time
    }

    private MagusOfTheUnseenDelayedTriggeredAbility(final MagusOfTheUnseenDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId) 
                && event.getTargetId().equals(this.getEffects().get(0).getTargetPointer().getFirst(game, this));
    }
    
    @Override
    public MagusOfTheUnseenDelayedTriggeredAbility copy() {
        return new MagusOfTheUnseenDelayedTriggeredAbility(this);
    }
    
    @Override
    public String getRule() {
        return "When you lose control of the artifact, tap it";
    }
}
