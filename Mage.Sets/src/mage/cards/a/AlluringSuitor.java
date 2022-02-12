package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlluringSuitor extends CardImpl {

    public AlluringSuitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.d.DeadlyDancer.class;

        // When you attack with exactly two creatures, transform Alluring Suitor.
        this.addAbility(new TransformAbility());
        this.addAbility(new AlluringSuitorTriggeredAbility());
    }

    private AlluringSuitor(final AlluringSuitor card) {
        super(card);
    }

    @Override
    public AlluringSuitor copy() {
        return new AlluringSuitor(this);
    }
}

class AlluringSuitorTriggeredAbility extends TriggeredAbilityImpl {

    AlluringSuitorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect());
    }

    private AlluringSuitorTriggeredAbility(final AlluringSuitorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AlluringSuitorTriggeredAbility copy() {
        return new AlluringSuitorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(game.getCombat().getAttackingPlayerId())
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(permanent -> permanent.isCreature(game))
                .count() == 2;
    }

    @Override
    public String getRule() {
        return "When you attack with exactly two creatures, transform {this}.";
    }
}
