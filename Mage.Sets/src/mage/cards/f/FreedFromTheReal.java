
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.UntapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public final class FreedFromTheReal extends CardImpl {

    public FreedFromTheReal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        this.subtype.add(SubType.AURA);
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Untap));
        this.addAbility(new EnchantAbility(auraTarget));
        // {U}: Tap enchanted creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapEnchantedEffect(), new ManaCostsImpl<>("{U}")));
        // {U}: Untap enchanted creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapEnchantedEffect(), new ManaCostsImpl<>("{U}")));

    }

    private FreedFromTheReal(final FreedFromTheReal card) {
        super(card);
    }

    @Override
    public FreedFromTheReal copy() {
        return new FreedFromTheReal(this);
    }
}
