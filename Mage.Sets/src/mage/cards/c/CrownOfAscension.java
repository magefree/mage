package mage.cards.c;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class CrownOfAscension extends CardImpl {

    public CrownOfAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature has flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        )));

        // Sacrifice Crown of Ascension: Enchanted creature and other creatures that share a creature type with it
        // gain flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilityAllEffect(
                        FlyingAbility.getInstance(),
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_CREATURE_ENCHANTED_AND_SHARE_TYPE
                ),
                new SacrificeSourceCost()
        ));
    }

    private CrownOfAscension(final CrownOfAscension card) {
        super(card);
    }

    @Override
    public CrownOfAscension copy() {
        return new CrownOfAscension(this);
    }
}
