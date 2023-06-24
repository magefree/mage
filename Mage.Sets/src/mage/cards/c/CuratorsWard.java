
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author keelahnkhan
 */
public final class CuratorsWard extends CardImpl {

    public CuratorsWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted permanent has hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(HexproofAbility.getInstance(), AttachmentType.AURA)
                        .setText("Enchanted permanent has hexproof")));

        // When enchanted permanent leaves the battlefield, if it was historic, draw two cards.
        this.addAbility(new CuratorsWardTriggeredAbility());
    }

    private CuratorsWard(final CuratorsWard card) {
        super(card);
    }

    @Override
    public CuratorsWard copy() {
        return new CuratorsWard(this);
    }
}

class CuratorsWardTriggeredAbility extends TriggeredAbilityImpl {

    public CuratorsWardTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2), false);
    }

    public CuratorsWardTriggeredAbility(CuratorsWardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
            Permanent enchanted = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (enchanted != null && enchanted.getAttachments().contains(getSourceId()) && enchanted.isHistoric(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted permanent leaves the battlefield, if it was historic, draw two cards. <i>(Artifacts, legendaries, and Sagas are historic.)</i>";
    }

    @Override
    public CuratorsWardTriggeredAbility copy() {
        return new CuratorsWardTriggeredAbility(this);
    }

}
