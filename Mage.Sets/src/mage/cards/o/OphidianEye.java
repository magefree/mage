
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class OphidianEye extends CardImpl {

    public OphidianEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.DrawCard));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Whenever enchanted creature deals damage to an opponent, you may draw a card.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new DrawCardSourceControllerEffect(1), "enchanted creature", true, false, false, TargetController.OPPONENT));

    }

    private OphidianEye(final OphidianEye card) {
        super(card);
    }

    @Override
    public OphidianEye copy() {
        return new OphidianEye(this);
    }
}
