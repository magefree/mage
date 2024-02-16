package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
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
 * @author TheElk801
 */
public final class PredatoryImpetus extends CardImpl {

    public PredatoryImpetus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +3/+3, must be blocked if able, and is goaded.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(3, 3));
        ability.addEffect(new MustBeBlockedByAtLeastOneAttachedEffect().concatBy(","));
        ability.addEffect(new GoadAttachedEffect().concatBy(","));
        this.addAbility(ability);
    }

    private PredatoryImpetus(final PredatoryImpetus card) {
        super(card);
    }

    @Override
    public PredatoryImpetus copy() {
        return new PredatoryImpetus(this);
    }
}
