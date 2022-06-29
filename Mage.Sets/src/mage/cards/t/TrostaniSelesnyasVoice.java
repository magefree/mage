

package mage.cards.t;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TrostaniSelesnyasVoice extends CardImpl {

    public TrostaniSelesnyasVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever another creature enters the battlefield under your control, you gain life equal to that creature's toughness.
        this.addAbility(new TrostaniSelesnyasVoiceTriggeredAbility());

        // {1}{G}{W}, {T}: Populate. (Create a token that's a copy of a creature token you control.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PopulateEffect(), new ManaCostsImpl<>("{1}{G}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TrostaniSelesnyasVoice(final TrostaniSelesnyasVoice card) {
        super(card);
    }

    @Override
    public TrostaniSelesnyasVoice copy() {
        return new TrostaniSelesnyasVoice(this);
    }
}

class TrostaniSelesnyasVoiceTriggeredAbility extends TriggeredAbilityImpl {

    public TrostaniSelesnyasVoiceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TrostaniSelesnyasVoiceEffect(), false);
    }

    public TrostaniSelesnyasVoiceTriggeredAbility(TrostaniSelesnyasVoiceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(this.controllerId)
                && !Objects.equals(event.getTargetId(), this.getSourceId())) {
            Effect effect = this.getEffects().get(0);
            // life is determined during resolution so it has to be retrieved there (e.g. Giant Growth before resolution)
            effect.setValue("lifeSource", event.getTargetId());
            effect.setValue("zoneChangeCounter", permanent.getZoneChangeCounter(game));
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever another creature enters the battlefield under your control, " ;
    }

    @Override
    public TrostaniSelesnyasVoiceTriggeredAbility copy() {
        return new TrostaniSelesnyasVoiceTriggeredAbility(this);
    }
}

class TrostaniSelesnyasVoiceEffect extends OneShotEffect {

    public TrostaniSelesnyasVoiceEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to that creature's toughness";
    }

    public TrostaniSelesnyasVoiceEffect(final TrostaniSelesnyasVoiceEffect effect) {
        super(effect);
    }

    @Override
    public TrostaniSelesnyasVoiceEffect copy() {
        return new TrostaniSelesnyasVoiceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("lifeSource");
        Integer zoneChangeCounter = (Integer) getValue("zoneChangeCounter");
        Permanent creature = game.getPermanent(creatureId);
        if (creature == null || creature.getZoneChangeCounter(game) != zoneChangeCounter) {
            creature = (Permanent) game.getLastKnownInformation(creatureId, Zone.BATTLEFIELD, zoneChangeCounter);
        }
        if (creature != null) {
            int amount = creature.getToughness().getValue();
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(amount, game, source);
            }
            return true;
        }
        return false;
    }
}
