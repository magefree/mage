package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class CrownOfFury extends CardImpl {

    public CrownOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature gets +1/+0 and has first strike.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA).setText("and has first strike"));
        this.addAbility(ability);

        // Sacrifice Crown of Fury: Enchanted creature and other creatures that share a creature type with it
        // get +1/+0 and gain first strike until end of turn.
        ability = new SimpleActivatedAbility(
                new BoostAllEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_CREATURE_ENCHANTED_AND_SHARE_TYPE, false)
                .setText("enchanted creature and other creatures that share a creature type with it get +1/+0"),
                new SacrificeSourceCost()
        );
        ability.addEffect(
                new GainAbilityAllEffect(
                        FirstStrikeAbility.getInstance(),
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_CREATURE_ENCHANTED_AND_SHARE_TYPE
                ).setText("and gain first strike until end of turn")
        );
        this.addAbility(ability);
    }

    private CrownOfFury(final CrownOfFury card) {
        super(card);
    }

    @Override
    public CrownOfFury copy() {
        return new CrownOfFury(this);
    }
}
