package mage.cards.c;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EnchantAbility;
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
public final class CrownOfVigor extends CardImpl {

    public CrownOfVigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 1)));

        // Sacrifice Crown of Vigor: Enchanted creature and other creatures that share a creature type with it
        // get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostAllEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_CREATURE_ENCHANTED_AND_SHARE_TYPE, false),
                new SacrificeSourceCost()
        ));
    }

    private CrownOfVigor(final CrownOfVigor card) {
        super(card);
    }

    @Override
    public CrownOfVigor copy() {
        return new CrownOfVigor(this);
    }
}
