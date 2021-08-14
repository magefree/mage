
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ProtectionAbility;
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
 * @author Galatolol
 */
public final class WallOfPutridFlesh extends CardImpl {

    public WallOfPutridFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
        // Prevent all damage that would be dealt to Wall of Putrid Flesh by enchanted creatures.
        // The term “enchanted creatures” means “creatures with an Aura on them”.
        Effect effect = new PreventDamageToSourceByEnchantedCreatures();
        effect.setText("Prevent all damage that would be dealt to {this} by enchanted creatures.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private WallOfPutridFlesh(final WallOfPutridFlesh card) {
        super(card);
    }

    @Override
    public WallOfPutridFlesh copy() {
        return new WallOfPutridFlesh(this);
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
