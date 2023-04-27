package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author jeffwadsworth
 */
public final class ValorOfTheWorthy extends CardImpl {

    public ValorOfTheWorthy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetCreaturePermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield)));
        
        // When enchanted creature leaves the battlefield, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new LeavesTheBattlefieldAttachedTriggeredAbility());

    }

    private ValorOfTheWorthy(final ValorOfTheWorthy card) {
        super(card);
    }

    @Override
    public ValorOfTheWorthy copy() {
        return new ValorOfTheWorthy(this);
    }
}

class LeavesTheBattlefieldAttachedTriggeredAbility extends ZoneChangeTriggeredAbility {

    public LeavesTheBattlefieldAttachedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SpiritWhiteToken()), "When enchanted creature leaves the battlefield, ", Boolean.FALSE);
    }

    public LeavesTheBattlefieldAttachedTriggeredAbility(final LeavesTheBattlefieldAttachedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LeavesTheBattlefieldAttachedTriggeredAbility copy() {
        return new LeavesTheBattlefieldAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(this.getSourceId());
        if (enchantment != null
                && enchantment.getAttachedTo() != null
                && event.getTargetId().equals(enchantment.getAttachedTo())) {
            if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if ((zEvent.getFromZone() == Zone.BATTLEFIELD)) {
                    return true;
                }
            }
        }
        return false;
    }
}
