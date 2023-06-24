
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author emerald000
 */
public final class HostileRealm extends CardImpl {

    public HostileRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted land has "{T}: Target creature can't block this turn."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBlockTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        gainedAbility.addTarget(new TargetCreaturePermanent());
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        effect.setText("Enchanted land has \"{T}: Target creature can't block this turn.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private HostileRealm(final HostileRealm card) {
        super(card);
    }

    @Override
    public HostileRealm copy() {
        return new HostileRealm(this);
    }
}
