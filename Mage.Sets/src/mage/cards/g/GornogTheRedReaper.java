package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.combat.CowardsCantBlockWarriorsEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GornogTheRedReaper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WARRIOR, "Attacking Warriors");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.COWARD, "Cowards your opponents control");

    static {
        filter.add(AttackingPredicate.instance);
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2, null);
    private static final Hint hint = new ValueHint(filter2.getMessage(), xValue);

    public GornogTheRedReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Cowards can't block Warriors.
        this.addAbility(new SimpleStaticAbility(new CowardsCantBlockWarriorsEffect()));

        // Whenever one or more Warriors you control attack a player, target creature that player controls becomes a Coward.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(Zone.BATTLEFIELD,
                new BecomesCreatureTypeTargetEffect(Duration.EndOfGame, SubType.COWARD),
                1, filter, true);
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);

        // Attacking Warriors you control get +X/+0, where X is the number of Cowards your opponents control.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield, filter, false
        )).addHint(hint));
    }

    private GornogTheRedReaper(final GornogTheRedReaper card) {
        super(card);
    }

    @Override
    public GornogTheRedReaper copy() {
        return new GornogTheRedReaper(this);
    }
}

class GornogTheRedReaperTriggeredAbility extends TriggeredAbilityImpl {

    GornogTheRedReaperTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BecomesCreatureTypeTargetEffect(Duration.EndOfGame, SubType.COWARD)
                .setText("target creature that player controls becomes a Coward"));
        this.setTriggerPhrase("Whenever one or more Warriors you control attack a player, ");
    }

    private GornogTheRedReaperTriggeredAbility(final GornogTheRedReaperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GornogTheRedReaperTriggeredAbility copy() {
        return new GornogTheRedReaperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPlayer(event.getTargetId()) == null
                || ((DefenderAttackedEvent) event)
                .getAttackers(game)
                .stream()
                .noneMatch(p -> p.hasSubtype(SubType.WARRIOR, game) && p.isControlledBy(getControllerId()))) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent("creature controlled by defending player");
        filter.add(new ControllerIdPredicate(event.getTargetId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }
}
