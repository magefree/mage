
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class MysticMight extends CardImpl {

    public MysticMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        this.subtype.add(SubType.AURA);

        // Enchant land you control
        TargetPermanent auraTarget = new TargetPermanent(new FilterControlledLandPermanent());
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Cumulative upkeep {1}{U}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}{U}")));
        // Enchanted land has "{tap}: Target creature gets +2/+2 until end of turn."
        Ability gainAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 2, Duration.EndOfTurn), new TapSourceCost());
        gainAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainAbility, AttachmentType.AURA,
            Duration.WhileOnBattlefield, "Enchanted land has \"{T}: Target creature gets +2/+2 until end of turn.\"")));
    }

    private MysticMight(final MysticMight card) {
        super(card);
    }

    @Override
    public MysticMight copy() {
        return new MysticMight(this);
    }
}
