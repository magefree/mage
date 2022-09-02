package mage.cards.w;

import java.util.UUID;
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
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
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
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has base power 0 and has “At the beginning of your upkeep, you lose 1 life unless you sacrifice this creature."
        Ability abilityTest = new SimpleStaticAbility(new SetBasePowerEnchantedEffect(0));
        Effect effect2 = new DoUnlessControllerPaysEffect(new LoseLifeSourceControllerEffect(1),
                new SacrificeSourceCost(), rule);
        effect2.setText("you lose 1 life unless you sacrifice this creature.");
        Effect effect3 = new GainAbilityAttachedEffect(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                effect2, TargetController.YOU, false, false, null), AttachmentType.AURA);
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
