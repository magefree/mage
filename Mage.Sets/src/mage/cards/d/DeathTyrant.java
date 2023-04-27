package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author weirddan455
 */
public final class DeathTyrant extends CardImpl {

    public DeathTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.BEHOLDER);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Negative Energy Cone — Whenever an attacking creature you control or a blocking creature an opponent controls dies, create a 2/2 black Zombie creature token.
        this.addAbility(new DeathTyrantTriggeredAbility().withFlavorWord("Negative Energy Cone"));

        // {5}{B}: Return Death Tyrant from your graveyard to the battlefield tapped.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true, false),
                new ManaCostsImpl<>("{5}{B}")
        ));
    }

    private DeathTyrant(final DeathTyrant card) {
        super(card);
    }

    @Override
    public DeathTyrant copy() {
        return new DeathTyrant(this);
    }
}

class DeathTyrantTriggeredAbility extends TriggeredAbilityImpl {

    public DeathTyrantTriggeredAbility() {
        super(Zone.ALL, new CreateTokenEffect(new ZombieToken()));
        setTriggerPhrase("Whenever an attacking creature you control or a blocking creature an opponent controls dies, ");
    }

    private DeathTyrantTriggeredAbility(final DeathTyrantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DeathTyrantTriggeredAbility copy() {
        return new DeathTyrantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(zEvent.getTargetId());
            if (permanent != null && permanent.isCreature(game)) {
                if (permanent.isControlledBy(controllerId) && permanent.isAttacking()) {
                    return true;
                }
                return game.getOpponents(controllerId).contains(permanent.getControllerId()) && permanent.getBlocking() > 0;
            }
        }
        return false;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, event, game);
    }
}
