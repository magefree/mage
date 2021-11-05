
package mage.cards.p;

import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public final class PathOfMettle extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that doesn't have first strike, double strike, vigilance, or haste");

    static {
        filter.add(Predicates.not(Predicates.or(
                new AbilityPredicate(FirstStrikeAbility.class),
                new AbilityPredicate(DoubleStrikeAbility.class),
                new AbilityPredicate(VigilanceAbility.class),
                new AbilityPredicate(HasteAbility.class)
        )));
    }

    public PathOfMettle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.m.MetzaliTowerOfTriumph.class;

        // When Path of Mettle enters the battlefield, it deals 1 damage to each creature that doesn't have first strike, double strike, vigilance, or haste.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(1, filter)));

        // Whenever you attack with at least two creatures that have first strike, double strike, vigilance, and/or haste, transform Path of Mettle.
        this.addAbility(new TransformAbility());
        this.addAbility(new PathOfMettleTriggeredAbility());
    }

    private PathOfMettle(final PathOfMettle card) {
        super(card);
    }

    @Override
    public PathOfMettle copy() {
        return new PathOfMettle(this);
    }
}

class PathOfMettleTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that doesn't have first strike, double strike, vigilance, or haste");

    static {
        filter.add(Predicates.or(
                new AbilityPredicate(FirstStrikeAbility.class),
                new AbilityPredicate(DoubleStrikeAbility.class),
                new AbilityPredicate(VigilanceAbility.class),
                new AbilityPredicate(HasteAbility.class)
        ));
    }

    public PathOfMettleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect());
    }

    public PathOfMettleTriggeredAbility(final PathOfMettleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PathOfMettleTriggeredAbility copy() {
        return new PathOfMettleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int attackerCount = 0;
        if (game.getCombat() != null) {
            if (isControlledBy(game.getCombat().getAttackingPlayerId())) {
                for (UUID attackerId : game.getCombat().getAttackers()) {
                    Permanent attacker = game.getPermanent(attackerId);
                    if (attacker != null && filter.match(attacker, game)) {
                        attackerCount++;
                    }
                }
                return attackerCount >= 2;
            }
        }
        return false;

    }

    @Override
    public String getRule() {
        return "Whenever you attack with at least two creatures that have first strike, double strike, vigilance, and/or haste, transform Path of Mettle";
    }
}