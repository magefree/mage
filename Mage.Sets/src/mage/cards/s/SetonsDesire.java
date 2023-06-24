
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SetonsDesire extends CardImpl {

    public SetonsDesire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield)));

        // Threshold - As long as seven or more cards are in your graveyard, all creatures able to block enchanted creature do so.
        Effect effect = new ConditionalRequirementEffect(new MustBeBlockedByAllAttachedEffect(AttachmentType.AURA), new CardsInControllerGraveyardCondition(7));
        effect.setText("As long as seven or more cards are in your graveyard, all creatures able to block enchanted creature do so");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private SetonsDesire(final SetonsDesire card) {
        super(card);
    }

    @Override
    public SetonsDesire copy() {
        return new SetonsDesire(this);
    }
}
