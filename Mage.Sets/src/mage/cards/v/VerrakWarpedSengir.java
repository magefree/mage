package mage.cards.v;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CopyStackAbilityEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VerrakWarpedSengir extends CardImpl {

    public VerrakWarpedSengir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you activate an ability that isn't a mana ability, if life was paid to activate it, you may pay that much life again. If you do, copy that ability. You may choose new targets for the copy.
        this.addAbility(new VerrakWarpedSengirTriggeredAbility());
    }

    private VerrakWarpedSengir(final VerrakWarpedSengir card) {
        super(card);
    }

    @Override
    public VerrakWarpedSengir copy() {
        return new VerrakWarpedSengir(this);
    }
}

class VerrakWarpedSengirTriggeredAbility extends TriggeredAbilityImpl {

    VerrakWarpedSengirTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private VerrakWarpedSengirTriggeredAbility(final VerrakWarpedSengirTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VerrakWarpedSengirTriggeredAbility copy() {
        return new VerrakWarpedSengirTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility == null || stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl) {
            return false;
        }
        int lifePaid = CardUtil.castStream(
                stackAbility
                        .getStackAbility()
                        .getCosts()
                        .stream(),
                PayLifeCost.class
        ).mapToInt(PayLifeCost::getLifePaid).sum();
        if (lifePaid > 0) {
            this.getEffects().clear();
            this.addEffect(new DoIfCostPaid(new CopyStackAbilityEffect(), new PayLifeCost(lifePaid)));
            this.getEffects().setValue("stackAbility", stackAbility);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability that isn't a mana ability, " +
                "if life was paid to activate it, you may pay that much life again. " +
                "If you do, copy that ability. You may choose new targets for the copy.";
    }
}
