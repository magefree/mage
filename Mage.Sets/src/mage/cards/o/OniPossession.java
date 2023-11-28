package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetCardSubtypeAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class OniPossession extends CardImpl {

    public OniPossession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of your upkeep, sacrifice a creature.
        Ability ability2 = new BeginningOfUpkeepTriggeredAbility(
                new SacrificeControllerEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, 1, ""), TargetController.YOU, false);
        this.addAbility(ability2);
        // Enchanted creature gets +3/+3 and has trample.
        Ability staticAbility = new SimpleStaticAbility(new BoostEnchantedEffect(3, 3));
        staticAbility.addEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA)
                .setText("and has trample"));
        this.addAbility(staticAbility);
        // Enchanted creature is a Demon Spirit.
        this.addAbility(new SimpleStaticAbility(new SetCardSubtypeAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA, SubType.DEMON, SubType.SPIRIT)));
    }

    private OniPossession(final OniPossession card) {
        super(card);
    }

    @Override
    public OniPossession copy() {
        return new OniPossession(this);
    }
}
