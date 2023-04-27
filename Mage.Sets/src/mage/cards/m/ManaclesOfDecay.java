
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class ManaclesOfDecay extends CardImpl {

    public ManaclesOfDecay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add(SubType.AURA);

        
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted creature can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAttachedEffect(AttachmentType.AURA)));
        // {B}: Enchanted creature gets -1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(-1, -1, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.B)));
        // {R}: Enchanted creature can't block this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBlockAttachedEffect(AttachmentType.AURA, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R)));

    }

    private ManaclesOfDecay(final ManaclesOfDecay card) {
        super(card);
    }

    @Override
    public ManaclesOfDecay copy() {
        return new ManaclesOfDecay(this);
    }
}
