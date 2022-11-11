package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HardCover extends CardImpl {

    public HardCover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +0/+2 and has "{T}: Draw a card, then discard a card."
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(0, 2));
        ability.addEffect(new GainAbilityAttachedEffect(new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new TapSourceCost()
        ), AttachmentType.AURA).setText("and has \"{T}: Draw a card, then discard a card.\""));
        this.addAbility(ability);
    }

    private HardCover(final HardCover card) {
        super(card);
    }

    @Override
    public HardCover copy() {
        return new HardCover(this);
    }
}
