
package mage.cards.o;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public final class Overgrowth extends CardImpl {

    public Overgrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted land is tapped for mana, its controller adds {G}{G}.
        this.addAbility(new OvergrowthTriggeredAbility());
    }

    public Overgrowth(final Overgrowth card) {
        super(card);
    }

    @Override
    public Overgrowth copy() {
        return new Overgrowth(this);
    }
}

class OvergrowthTriggeredAbility extends TriggeredManaAbility {


    public OvergrowthTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaToManaPoolTargetControllerEffect(Mana.GreenMana(2), "their"));
    }

    public OvergrowthTriggeredAbility(final OvergrowthTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OvergrowthTriggeredAbility copy() {
        return new OvergrowthTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null && enchantment.isAttachedTo(event.getSourceId())) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                getEffects().get(0).setTargetPointer(new FixedTarget(enchanted.getControllerId()));
                return true;
            }
        }
        return false;
    }


    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds {G}{G}";
    }
}
