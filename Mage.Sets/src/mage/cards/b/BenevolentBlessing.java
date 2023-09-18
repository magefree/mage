package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.keyword.ProtectionChosenColorAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BenevolentBlessing extends CardImpl {

    public BenevolentBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // As Benevolent Blessing enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // Enchanted creature has protection from the chosen color. This effect doesn't remove Auras and Equipment you control that are already attached to it.
        this.addAbility(new SimpleStaticAbility(
                new ProtectionChosenColorAttachedEffect(false)
                        .setNotRemoveControlled(true)
                        .setText("Enchanted creature has protection from the chosen color. This effect doesn't " +
                                "remove Auras and Equipment you control that are already attached to it.")
        ));
    }

    private BenevolentBlessing(final BenevolentBlessing card) {
        super(card);
    }

    @Override
    public BenevolentBlessing copy() {
        return new BenevolentBlessing(this);
    }
}
