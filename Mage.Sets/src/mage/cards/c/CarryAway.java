
package mage.cards.c;

import java.util.UUID;
import mage.target.common.TargetEquipmentPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class CarryAway extends CardImpl {

    public CarryAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant Equipment
        TargetPermanent auraTarget = new TargetEquipmentPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Carry Away enters the battlefield, unattach enchanted Equipment.
        ability = new EntersBattlefieldTriggeredAbility(new CarryAwayEffect());
        this.addAbility(ability);
        // You control enchanted Equipment.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect("equipment")));
    }

    private CarryAway(final CarryAway card) {
        super(card);
    }

    @Override
    public CarryAway copy() {
        return new CarryAway(this);
    }
}

class CarryAwayEffect extends OneShotEffect {

    public CarryAwayEffect() {
        super(Outcome.Detriment);
        this.staticText = "unattach enchanted equipment.";
    }

    public CarryAwayEffect(final CarryAwayEffect effect) {
        super(effect);
    }

    @Override
    public CarryAwayEffect copy() {
        return new CarryAwayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Permanent enchantedEquipment = game.getPermanent(permanent.getAttachedTo());
            if (enchantedEquipment != null) {
                Permanent equippedCreature = game.getPermanent(enchantedEquipment.getAttachedTo());
                if (equippedCreature != null) {
                    return equippedCreature.removeAttachment(enchantedEquipment.getId(), source, game);
                }
            }
        }
        return false;
    }
}
