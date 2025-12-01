package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.HumanToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JerrenCorruptedBishop extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.HUMAN);

    public JerrenCorruptedBishop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{2}{B}",
                "Ormendahl, the Corrupter",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DEMON}, "B");

        // Jerren, Corrupted Bishop
        this.getLeftHalfCard().setPT(2, 3);

        // Whenever Jerren, Corrupted Bishop enters the battlefield or another nontoken Human you control dies, you lose 1 life and create a 1/1 white Human creature token.
        this.getLeftHalfCard().addAbility(new JerrenCorruptedBishopTriggeredAbility());

        // {2}: Target Human you control gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.YOU, new DoIfCostPaid(
                new TransformSourceEffect(), new ManaCostsImpl<>("{4}{B}{B}")
        ), false, JerrenCorruptedBishopCondition.instance));

        // Ormendahl, the Corrupter
        this.getRightHalfCard().setPT(6, 6);

        // Flying, Trample, Lifelink
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // Sacrifice another creature: Draw a card.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        ));
    }

    private JerrenCorruptedBishop(final JerrenCorruptedBishop card) {
        super(card);
    }

    @Override
    public JerrenCorruptedBishop copy() {
        return new JerrenCorruptedBishop(this);
    }
}

enum JerrenCorruptedBishopCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getLife() == 13;
    }

    @Override
    public String toString() {
        return "if you have exactly 13 life";
    }
}

class JerrenCorruptedBishopTriggeredAbility extends TriggeredAbilityImpl {

    JerrenCorruptedBishopTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(1));
        this.addEffect(new CreateTokenEffect(new HumanToken()));
        setLeavesTheBattlefieldTrigger(true);
    }

    private JerrenCorruptedBishopTriggeredAbility(final JerrenCorruptedBishopTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JerrenCorruptedBishopTriggeredAbility copy() {
        return new JerrenCorruptedBishopTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                return event.getTargetId().equals(getSourceId());
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                return zEvent.isDiesEvent()
                        && zEvent.getTarget() != null
                        && !zEvent.getTarget().getId().equals(getSourceId())
                        && zEvent.getTarget().isControlledBy(getControllerId())
                        && !(zEvent.getTarget() instanceof PermanentToken)
                        && zEvent.getTarget().hasSubtype(SubType.HUMAN, game);
            default:
                return false;
        }
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters or another nontoken Human you control dies, " +
                "you lose 1 life and create a 1/1 white Human creature token.";
    }
}
