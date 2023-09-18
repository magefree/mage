
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.permanent.token.SaprolingToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class VerdantEmbrace extends CardImpl {

    public VerdantEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature gets +3/+3 and has "At the beginning of each upkeep, create a 1/1 green Saproling creature token."
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 3, Duration.WhileOnBattlefield));
        Ability grantedAbility = new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new SaprolingToken()), TargetController.ANY, false);
        Effect effect = new GainAbilityAttachedEffect(grantedAbility, AttachmentType.AURA);
        effect.setText("and has \"At the beginning of each upkeep, create a 1/1 green Saproling creature token.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private VerdantEmbrace(final VerdantEmbrace card) {
        super(card);
    }

    @Override
    public VerdantEmbrace copy() {
        return new VerdantEmbrace(this);
    }
}
