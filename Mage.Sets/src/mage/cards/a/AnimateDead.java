package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.AnimateDeadTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

/**
 * @author LevelX2, awjackson
 */
public final class AnimateDead extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card in a graveyard");

    public AnimateDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature card in a graveyard
        TargetCardInGraveyard auraTarget = new TargetCardInGraveyard(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        this.addAbility(new AnimateDeadTriggeredAbility());

        // Enchanted creature gets -1/-0.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(-1, 0)));
    }

    private AnimateDead(final AnimateDead card) {
        super(card);
    }

    @Override
    public AnimateDead copy() {
        return new AnimateDead(this);
    }
}
