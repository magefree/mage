
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public final class MortalObstinacy extends CardImpl {

    public MortalObstinacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1,1, Duration.WhileOnBattlefield)));
        // Whenever enchanted creature deals combat damage to a player, you may sacrifice Mortal Obstinacy. If you do, destroy target enchantment.
        this.addAbility(new MortalObstinacyAbility());

    }

    private MortalObstinacy(final MortalObstinacy card) {
        super(card);
    }

    @Override
    public MortalObstinacy copy() {
        return new MortalObstinacy(this);
    }
}

class MortalObstinacyAbility extends TriggeredAbilityImpl {

    public MortalObstinacyAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DestroyTargetEffect(), new SacrificeSourceCost()));
        addTarget(new TargetEnchantmentPermanent());
        setTriggerPhrase("Whenever enchanted creature deals combat damage to a player, ");
    }

    public MortalObstinacyAbility(final MortalObstinacyAbility ability) {
        super(ability);
    }

    @Override
    public MortalObstinacyAbility copy() {
        return new MortalObstinacyAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        Permanent damageMakingCreature = game.getPermanent(event.getSourceId());
        return damageEvent.isCombatDamage() && damageMakingCreature != null && damageMakingCreature.getAttachments().contains(this.getSourceId());
    }
}

