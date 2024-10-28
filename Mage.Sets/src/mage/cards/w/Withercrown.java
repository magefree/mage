package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Withercrown extends CardImpl {

    public Withercrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        final String rule = "Do you want to sacrifice the enchanted creature?  If not, you lose 1 life.";

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has base power 0 and has “At the beginning of your upkeep, you lose 1 life unless you sacrifice this creature."
        Ability abilityTest = new SimpleStaticAbility(new SetBasePowerEnchantedEffect(0));
        Effect effect2 = new DoUnlessControllerPaysEffect(new LoseLifeSourceControllerEffect(1),
                new SacrificeSourceCost(), rule);
        effect2.setText("you lose 1 life unless you sacrifice this creature.");
        Effect effect3 = new GainAbilityAttachedEffect(new BeginningOfUpkeepTriggeredAbility(
                effect2, TargetController.YOU, false, false), AttachmentType.AURA);
        effect3.setText("and has \"At the beginning of your upkeep, you lose 1 life unless you sacrifice this creature.\"");
        abilityTest.addEffect(effect3);
        this.addAbility(abilityTest);

    }

    private Withercrown(final Withercrown card) {
        super(card);
    }

    @Override
    public Withercrown copy() {
        return new Withercrown(this);
    }
}
