package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FootfallCrater extends CardImpl {

    public FootfallCrater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted land has "{T}: Target creature gains trample and haste until end of turn."
        ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("Target creature gains trample"), new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and haste until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ability, AttachmentType.AURA, Duration.WhileOnBattlefield,
                "enchanted land has \"{T}: Target creature gains trample and haste until end of turn.\""
        )));

        // Cycling {1}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private FootfallCrater(final FootfallCrater card) {
        super(card);
    }

    @Override
    public FootfallCrater copy() {
        return new FootfallCrater(this);
    }
}
