package mage.cards.p;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class ProdigiousGrowth extends CardImpl {

    public ProdigiousGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +7/+7 and has trample.
        ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostEnchantedEffect(7, 7, Duration.WhileOnBattlefield)
        );
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(),
                AttachmentType.AURA
        )
                .setText("and has trample"));
        this.addAbility(ability);
    }

    private ProdigiousGrowth(final ProdigiousGrowth card) {
        super(card);
    }

    @Override
    public ProdigiousGrowth copy() {
        return new ProdigiousGrowth(this);
    }
}
