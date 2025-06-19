package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class CallOfTheFullMoon extends CardImpl {

    public CallOfTheFullMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +3/+2 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(3, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.AURA
        ).setText("and has trample"));
        this.addAbility(ability);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, sacrifice Call of the Full Moon.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY, new SacrificeSourceEffect(), false
        ).withInterveningIf(TwoOrMoreSpellsWereCastLastTurnCondition.instance));
    }

    private CallOfTheFullMoon(final CallOfTheFullMoon card) {
        super(card);
    }

    @Override
    public CallOfTheFullMoon copy() {
        return new CallOfTheFullMoon(this);
    }
}
