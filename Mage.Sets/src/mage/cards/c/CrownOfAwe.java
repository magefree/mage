package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class CrownOfAwe extends CardImpl {

    public CrownOfAwe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature has protection from black and from red.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED), AttachmentType.AURA
        )));

        // Sacrifice Crown of Awe: Enchanted creature and other creatures that share a creature type with it
        // gain protection from black and from red until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilityAllEffect(
                        ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED),
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_CREATURE_ENCHANTED_AND_SHARE_TYPE
                ),
                new SacrificeSourceCost()
        ));
    }

    private CrownOfAwe(final CrownOfAwe card) {
        super(card);
    }

    @Override
    public CrownOfAwe copy() {
        return new CrownOfAwe(this);
    }
}
