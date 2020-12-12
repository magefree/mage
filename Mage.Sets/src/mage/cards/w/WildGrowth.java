package mage.cards.w;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class WildGrowth extends CardImpl {

    public WildGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
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

    private WildGrowth(final WildGrowth card) {
        super(card);
    }

    @Override
    public WildGrowth copy() {
        return new WildGrowth(this);
    }
}

class WildGrowthTriggeredAbility extends TriggeredManaAbility {


    WildGrowthTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.G), "their"));
    }

    private WildGrowthTriggeredAbility(final WildGrowthTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WildGrowthTriggeredAbility copy() {
        return new WildGrowthTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo())) {
            Permanent enchantedLand = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
            if (enchantedLand != null && enchantedLand.isLand()) {
                this.getEffects().setTargetPointer(new FixedTarget(enchantedLand.getControllerId()));
                return true;
            }
        }
        return false;
    }


    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds an additional {G}";
    }
}
