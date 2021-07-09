
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class EnchantedBeing extends CardImpl {

    public EnchantedBeing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prevent all damage that would be dealt to Enchanted Being by enchanted creatures.
        Effect effect = new PreventDamageToSourceByEnchantedCreatures();
        effect.setText("Prevent all damage that would be dealt to {this} by enchanted creatures.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private EnchantedBeing(final EnchantedBeing card) {
        super(card);
    }

    @Override
    public EnchantedBeing copy() {
        return new EnchantedBeing(this);
    }
}

class PreventDamageToSourceByEnchantedCreatures extends PreventAllDamageToSourceEffect {

    public PreventDamageToSourceByEnchantedCreatures() {
        super(Duration.WhileOnBattlefield);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (isEnchantedCreature(game.getObject(event.getSourceId()), game)) {
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEnchantedCreature(MageObject input, Game game) {
        if (input == null || input.isCreature(game)) {
            return false;
        }
        for (UUID attachmentId : ((Permanent) input).getAttachments()) {
            Permanent attachment = game.getPermanent(attachmentId);
            if (attachment != null && attachment.isEnchantment(game)) {
                return true;
            }
        }
        return false;
    }
}
