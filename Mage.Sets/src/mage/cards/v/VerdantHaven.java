
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.AddManaAnyColorAttachedControllerEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
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

/**
 *
 * @author LevelX2
 */
public final class VerdantHaven extends CardImpl {

    public VerdantHaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Verdant Haven enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));

        // Whenever enchanted land is tapped for mana, its controller adds one mana of any color.
        this.addAbility(new VerdantHavenTriggeredAbility());
    }

    public VerdantHaven(final VerdantHaven card) {
        super(card);
    }

    @Override
    public VerdantHaven copy() {
        return new VerdantHaven(this);
    }
}

class VerdantHavenTriggeredAbility extends TriggeredManaAbility {

    public VerdantHavenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaAnyColorAttachedControllerEffect());
    }

    public VerdantHavenTriggeredAbility(final VerdantHavenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VerdantHavenTriggeredAbility copy() {
        return new VerdantHavenTriggeredAbility(this);
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
