
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
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
public final class EpharasRadiance extends CardImpl {

    public EpharasRadiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature has "{1}{W}, {T}: You gain 3 life."
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.AURA, Duration.WhileOnBattlefield)));
    }

    private EpharasRadiance(final EpharasRadiance card) {
        super(card);
    }

    @Override
    public EpharasRadiance copy() {
        return new EpharasRadiance(this);
    }
}
