package mage.cards.z;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.*;
import mage.abilities.keyword.MobilizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.TargetPointer;

/**
 *
 * @author Jmlundeen
 */
public final class ZurgoStormrender extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature token you control");

    static {
        filter.add(TokenPredicate.TRUE);

    }
    public ZurgoStormrender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mobilize 1
        this.addAbility(new MobilizeAbility(1));

        // Whenever a creature token you control leaves the battlefield, draw a card if it was attacking. Otherwise, each opponent loses 1 life.
        ConditionalOneShotEffect effect = new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1), TargetWasAttackingCondition.instance);
        effect.addOtherwiseEffect(new LoseLifeOpponentsEffect(1));
        effect.setText("draw a card if it was attacking. Otherwise, each opponent loses 1 life.");
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, effect, filter, false, SetTargetPointer.PERMANENT));
    }

    private ZurgoStormrender(final ZurgoStormrender card) {
        super(card);
    }

    @Override
    public ZurgoStormrender copy() {
        return new ZurgoStormrender(this);
    }
}

enum TargetWasAttackingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        TargetPointer target = source.getEffects().stream().map(Effect::getTargetPointer).filter(Objects::nonNull).findFirst().orElse(null);
        if (target == null) {
            return false;
        }
        Permanent creature = game.getPermanentOrLKIBattlefield(target.getFirst(game, source));
        if (creature == null) {
            return false;
        }
        return creature.isAttacking();
    }

    @Override
    public String toString() {
        return "if it was attacking";
    }
}