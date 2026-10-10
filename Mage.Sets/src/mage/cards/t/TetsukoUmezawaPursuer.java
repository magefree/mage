package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author miesma
 */
public final class TetsukoUmezawaPursuer extends CardImpl {

    public TetsukoUmezawaPursuer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever a creature an opponent controls with power or toughness 1 or less blocks, Tetsuko Umezawa deals 1 damage to that creature’s controller.
        this.addAbility(new TetsukoUmezawaPursuerTrigger());
    }

    private TetsukoUmezawaPursuer(final TetsukoUmezawaPursuer card) {
        super(card);
    }

    @Override
    public TetsukoUmezawaPursuer copy() {
        return new TetsukoUmezawaPursuer(this);
    }
}

class TetsukoUmezawaPursuerTrigger extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with power or toughness 1 or less");
    static {
        filter.add(
            Predicates.or(
                new PowerPredicate(ComparisonType.OR_LESS, 1),
                new ToughnessPredicate(ComparisonType.OR_LESS, 1)));
    }

    public TetsukoUmezawaPursuerTrigger() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1)
            .withTargetDescription("that creature's controller"));
        setTriggerPhrase("Whenever a creature an opponent controls with power or toughness 1 or less blocks, ");
    }

    private TetsukoUmezawaPursuerTrigger(final TetsukoUmezawaPursuerTrigger ability) {
        super(ability);
    }

    @Override
    public TetsukoUmezawaPursuerTrigger copy() {
        return new TetsukoUmezawaPursuerTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent blocker = game.getPermanent(event.getSourceId());
        Player controller = game.getPlayer(this.getControllerId());
        if (blocker != null && filter.match(blocker, game) && game.isOpponent(controller, blocker.getControllerId())) {
            getEffects().get(0).setTargetPointer(new FixedTarget(blocker.getControllerId()));
            return true;
        }
        return false;
    }

}

