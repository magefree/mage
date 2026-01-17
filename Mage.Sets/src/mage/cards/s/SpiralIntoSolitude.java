package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileAttachedEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
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
public final class SpiralIntoSolitude extends CardImpl {

    public SpiralIntoSolitude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature can't attack or block.
        this.addAbility(new SimpleStaticAbility(new CantAttackBlockAttachedEffect(AttachmentType.AURA)));

        // {1}{W}, Blight 1, Sacrifice this Aura: Exile enchanted creature.
        Ability ability = new SimpleActivatedAbility(new ExileAttachedEffect(), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new BlightCost(1));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SpiralIntoSolitude(final SpiralIntoSolitude card) {
        super(card);
    }

    @Override
    public SpiralIntoSolitude copy() {
        return new SpiralIntoSolitude(this);
    }
}
