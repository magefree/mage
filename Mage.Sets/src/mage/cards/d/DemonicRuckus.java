package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PlotAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DemonicRuckus extends CardImpl {

    public DemonicRuckus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+1 and has menace and trample.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(new MenaceAbility(false), AttachmentType.AURA).setText("and has menace"));
        ability.addEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA).setText("and trample"));
        this.addAbility(ability);

        // When Demonic Ruckus is put into a graveyard from the battlefield, draw a card.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
        ));

        // Plot {R}
        this.addAbility(new PlotAbility("{R}"));
    }

    private DemonicRuckus(final DemonicRuckus card) {
        super(card);
    }

    @Override
    public DemonicRuckus copy() {
        return new DemonicRuckus(this);
    }
}
