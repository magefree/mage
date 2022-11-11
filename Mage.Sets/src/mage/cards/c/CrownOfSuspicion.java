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
public final class CrownOfSuspicion extends CardImpl {

    public CrownOfSuspicion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Removal));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/-1.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(2, -1)));

        // Sacrifice Crown of Suspicion: Enchanted creature and other creatures that share a creature type with it
        // get +2/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostAllEffect(2, -1, Duration.EndOfTurn, StaticFilters.FILTER_CREATURE_ENCHANTED_AND_SHARE_TYPE, false),
                new SacrificeSourceCost()
        ));
    }

    private CrownOfSuspicion(final CrownOfSuspicion card) {
        super(card);
    }

    @Override
    public CrownOfSuspicion copy() {
        return new CrownOfSuspicion(this);
    }
}
