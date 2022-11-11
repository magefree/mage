package mage.cards.u;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class UnconventionalTactics extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Zombie");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public UnconventionalTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Target creature gets +3/+3 and gains flying until end of turn.
        Effect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/+3");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Whenever a Zombie enters the battlefield under your control, you may pay {W}. If you do, return Unconventional Tactics from your graveyard to your hand.
        this.addAbility(new UnconventionalTacticsTriggeredAbility());
    }

    private UnconventionalTactics(final UnconventionalTactics card) {
        super(card);
    }

    @Override
    public UnconventionalTactics copy() {
        return new UnconventionalTactics(this);
    }
}

class UnconventionalTacticsTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Zombie");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public UnconventionalTacticsTriggeredAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnToHandSourceEffect(), new ManaCostsImpl<>("{W}")), false);
    }

    public UnconventionalTacticsTriggeredAbility(final UnconventionalTacticsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UnconventionalTacticsTriggeredAbility copy() {
        return new UnconventionalTacticsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(this.controllerId)
                && filter.match(permanent, game);
    }

    @Override
    public String getRule() {
        return "Whenever a Zombie enters the battlefield under your control, you may pay {W}. If you do, return {this} from your graveyard to your hand.";
    }
}
