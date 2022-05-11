package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DesertControlledOrGraveyardCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantBlockAttackActivateAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DesertsHold extends CardImpl {

    public DesertsHold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Removal));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // When Desert's Hold enters the battlefield, if you control a Desert or there is a Desert card in your graveyard, you gain 3 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)),
                DesertControlledOrGraveyardCondition.instance, "When {this} enters the battlefield, " +
                "if you control a Desert or there is a Desert card in your graveyard, you gain 3 life."
        ).addHint(DesertControlledOrGraveyardCondition.getHint()));

        // Enchanted creature can't attack or block, and its activated abilities can't be activated.
        this.addAbility(new SimpleStaticAbility(new CantBlockAttackActivateAttachedEffect()));
    }

    private DesertsHold(final DesertsHold card) {
        super(card);
    }

    @Override
    public DesertsHold copy() {
        return new DesertsHold(this);
    }
}
