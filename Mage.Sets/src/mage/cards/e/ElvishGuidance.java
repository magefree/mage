package mage.cards.e;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 * @author Eirkei
 */
public final class ElvishGuidance extends CardImpl {

    public ElvishGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutManaInPool));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted land is tapped for mana, its controller adds {G} for each Elf on the battlefield.
        this.addAbility(new ElvishGuidanceTriggeredAbility());
    }

    public ElvishGuidance(final ElvishGuidance card) {
        super(card);
    }

    @Override
    public ElvishGuidance copy() {
        return new ElvishGuidance(this);
    }
}

class ElvishGuidanceTriggeredAbility extends TriggeredManaAbility {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public ElvishGuidanceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DynamicManaEffect(Mana.GreenMana(1), xValue));
    }

    public ElvishGuidanceTriggeredAbility(final ElvishGuidanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ElvishGuidanceTriggeredAbility copy() {
        return new ElvishGuidanceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        return enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo());
    }

    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds {G} for each Elf on the battlefield.";
    }
}
