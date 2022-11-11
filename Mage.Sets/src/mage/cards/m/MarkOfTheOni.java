

package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MarkOfTheOni extends CardImpl {

    public MarkOfTheOni(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));

        // At the beginning of the end step, if you control no Demons, sacrifice Mark of the Oni.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD,
                new SacrificeSourceEffect(),
                TargetController.ANY,
                new PermanentsOnTheBattlefieldCondition(
                        new FilterControlledCreaturePermanent(SubType.DEMON, "if you control no Demons"),
                        ComparisonType.FEWER_THAN, 1),
                false));
    }

    private MarkOfTheOni(final MarkOfTheOni card) {
        super(card);
    }

    @Override
    public MarkOfTheOni copy() {
        return new MarkOfTheOni(this);
    }
}
