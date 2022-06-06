
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByTargetSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FearsomeTemper extends CardImpl {

    public FearsomeTemper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 and has "{2}{R}: Target creature can't block this creature this turn."
        Effect effect = new BoostEnchantedEffect(2,2, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +2/+2");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        Ability grantedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedByTargetSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}{R}"));
        grantedAbility.addTarget(new TargetCreaturePermanent());
        effect = new GainAbilityAttachedEffect(grantedAbility, AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("and has \"{2}{R}: Target creature can't block this creature this turn");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private FearsomeTemper(final FearsomeTemper card) {
        super(card);
    }

    @Override
    public FearsomeTemper copy() {
        return new FearsomeTemper(this);
    }
}
