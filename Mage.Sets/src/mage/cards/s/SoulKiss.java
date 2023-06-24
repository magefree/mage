
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class SoulKiss extends CardImpl {

    public SoulKiss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // {B}, Pay 1 life: Enchanted creature gets +2/+2 until end of turn. Activate this ability no more than three times each turn.
        ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 2, Duration.EndOfTurn),
            new ManaCostsImpl<>("{B}"), 3);
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private SoulKiss(final SoulKiss card) {
        super(card);
    }

    @Override
    public SoulKiss copy() {
        return new SoulKiss(this);
    }
}
