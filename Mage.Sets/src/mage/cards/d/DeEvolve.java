package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileAttachedEffect;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.SetCardSubtypeAttachedEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class DeEvolve extends CardImpl {

    public DeEvolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets -4/-0, can't block, and is a Salamander.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(-4, 0));
        ability.addEffect(new CantBlockAttachedEffect(AttachmentType.AURA).setText(", can't block"));
        ability.addEffect(new SetCardSubtypeAttachedEffect(
            Duration.WhileOnBattlefield, AttachmentType.AURA, SubType.SALAMANDER
        ).setText(", and is a Salamander"));
        this.addAbility(ability);

        // Threshold -- At the beginning of each upkeep, if there are seven or more cards in your graveyard, exile enchanted creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(TargetController.ANY, new ExileAttachedEffect(), false)
            .withInterveningIf(ThresholdCondition.instance).setAbilityWord(AbilityWord.THRESHOLD)
            .addHint(new ConditionHint(ThresholdCondition.instance)));
    }

    private DeEvolve(final DeEvolve card) {
        super(card);
    }

    @Override
    public DeEvolve copy() {
        return new DeEvolve(this);
    }
}
