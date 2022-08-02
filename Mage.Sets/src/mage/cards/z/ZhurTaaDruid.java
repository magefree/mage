package mage.cards.z;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ZhurTaaDruid extends CardImpl {

    public ZhurTaaDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");
        this.subtype.add(SubType.HUMAN, SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // Whenever you tap Zhur-Taa Druid for mana, it deals 1 damage to each opponent.
        this.addAbility(new ZhurTaaDruidAbility());
    }

    private ZhurTaaDruid(final ZhurTaaDruid card) {
        super(card);
    }

    @Override
    public ZhurTaaDruid copy() {
        return new ZhurTaaDruid(this);
    }
}

class ZhurTaaDruidAbility extends TriggeredAbilityImpl {

    ZhurTaaDruidAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT));
        setTriggerPhrase("Whenever you tap {this} for mana, ");
    }

    private ZhurTaaDruidAbility(final ZhurTaaDruidAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // it's non mana triggered ability, so ignore it on checking, see TAPPED_FOR_MANA
        if (game.inCheckPlayableState()) {
            return false;
        }
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        return permanent != null
                && permanent == getSourcePermanentOrLKI(game)
                && isControlledBy(event.getPlayerId());
    }

    @Override
    public ZhurTaaDruidAbility copy() {
        return new ZhurTaaDruidAbility(this);
    }
}
