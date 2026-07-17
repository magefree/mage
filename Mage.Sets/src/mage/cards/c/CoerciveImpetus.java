package mage.cards.c;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CoerciveImpetus extends CardImpl {

    public CoerciveImpetus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+1 and is goaded.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new GoadAttachedEffect());
        this.addAbility(ability);

        // Whenever enchanted creature attacks, you draw a card and lose 1 life.
        Ability ability2 = new AttacksAttachedTriggeredAbility(
            new DrawCardSourceControllerEffect(1),
            AttachmentType.AURA,
            false
        );
        ability2.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability2);
    }

    private CoerciveImpetus(final CoerciveImpetus card) {
        super(card);
    }

    @Override
    public CoerciveImpetus copy() {
        return new CoerciveImpetus(this);
    }
}
