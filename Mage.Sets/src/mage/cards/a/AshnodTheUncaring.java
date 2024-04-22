package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshnodTheUncaring extends CardImpl {

    public AshnodTheUncaring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever you activate an ability of an artifact or creature that isn't a mana ability, if one or more permanents were sacrificed to activate it, you may copy that ability. You may choose new targets for the copy.
        this.addAbility(new AshnodTheUncaringTriggeredAbility());
    }

    private AshnodTheUncaring(final AshnodTheUncaring card) {
        super(card);
    }

    @Override
    public AshnodTheUncaring copy() {
        return new AshnodTheUncaring(this);
    }
}

class AshnodTheUncaringTriggeredAbility extends TriggeredAbilityImpl {

    AshnodTheUncaringTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyStackObjectEffect(), true);
    }

    private AshnodTheUncaringTriggeredAbility(final AshnodTheUncaringTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AshnodTheUncaringTriggeredAbility copy() {
        return new AshnodTheUncaringTriggeredAbility(this);
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
        if (stackAbility == null
                || stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl
                || stackAbility
                .getStackAbility()
                .getCosts()
                .stream()
                .noneMatch(SacrificeCost.class::isInstance)) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(stackAbility.getSourceId());
        if (permanent == null || (!permanent.isArtifact(game) && !permanent.isCreature(game))) {
            return false;
        }
        this.getEffects().setValue("stackObject", stackAbility);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability of an artifact or creature that isn't a mana ability, " +
                "if one or more permanents were sacrificed to activate it, " +
                "you may copy that ability. You may choose new targets for the copy.";
    }
}
