
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.mana.AddManaAnyColorAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ShroudAbility;
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

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TraceOfAbundance extends CardImpl {

    private static final String rule = "Enchanted land has shroud";

    public TraceOfAbundance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R/W}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted land has shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ShroudAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, rule)));

        // Whenever enchanted land is tapped for mana, its controller adds one mana of any color.
        this.addAbility(new TraceOfAbundanceTriggeredAbility());
    }

    public TraceOfAbundance(final TraceOfAbundance card) {
        super(card);
    }

    @Override
    public TraceOfAbundance copy() {
        return new TraceOfAbundance(this);
    }
}

class TraceOfAbundanceTriggeredAbility extends TriggeredManaAbility {

    public TraceOfAbundanceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaAnyColorAttachedControllerEffect());
    }

    public TraceOfAbundanceTriggeredAbility(final TraceOfAbundanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TraceOfAbundanceTriggeredAbility copy() {
        return new TraceOfAbundanceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        return enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo());
    }

    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds one mana of any color.";
    }
}
