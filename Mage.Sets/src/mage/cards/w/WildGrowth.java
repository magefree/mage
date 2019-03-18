
package mage.cards.w;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
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
public final class WildGrowth extends CardImpl {

    public WildGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted land is tapped for mana, its controller adds {G}.
        this.addAbility(new WildGrowthTriggeredAbility());
    }

    public WildGrowth(final WildGrowth card) {
        super(card);
    }

    @Override
    public WildGrowth copy() {
        return new WildGrowth(this);
    }
}

class WildGrowthTriggeredAbility extends TriggeredManaAbility {


    public WildGrowthTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.G), "their"));
    }

    public WildGrowthTriggeredAbility(final WildGrowthTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WildGrowthTriggeredAbility copy() {
        return new WildGrowthTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                for(Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent.getControllerId()));
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds {G}";
    }
}
