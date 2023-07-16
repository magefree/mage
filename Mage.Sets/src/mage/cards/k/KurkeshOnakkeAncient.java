package mage.cards.k;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class KurkeshOnakkeAncient extends CardImpl {

    public KurkeshOnakkeAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever you activate an ability of an artifact, if it isn't a mana ability, you may pay {R}.  If you do, copy that ability.  You may choose new targets for the copy.
        this.addAbility(new KurkeshOnakkeAncientTriggeredAbility());
    }

    private KurkeshOnakkeAncient(final KurkeshOnakkeAncient card) {
        super(card);
    }

    @Override
    public KurkeshOnakkeAncient copy() {
        return new KurkeshOnakkeAncient(this);
    }
}

class KurkeshOnakkeAncientTriggeredAbility extends TriggeredAbilityImpl {

    KurkeshOnakkeAncientTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CopyStackObjectEffect(), new ManaCostsImpl<>("{R}")));
        setTriggerPhrase("Whenever you activate an ability of an artifact, if it isn't a mana ability, ");
    }

    KurkeshOnakkeAncientTriggeredAbility(final KurkeshOnakkeAncientTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KurkeshOnakkeAncientTriggeredAbility copy() {
        return new KurkeshOnakkeAncientTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (source == null || !source.isArtifact(game)) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility == null || stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl) {
            return false;
        }
        this.getEffects().setValue("stackObject", stackAbility);
        return true;
    }
}
