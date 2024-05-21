
package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.UmbraArmorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class CrabUmbra extends CardImpl {

    public CrabUmbra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {2}{U}: Untap enchanted creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapAttachedEffect(), new ManaCostsImpl<>("{2}{U}")));

        // Umbra armor
        this.addAbility(new UmbraArmorAbility());
    }

    private CrabUmbra(final CrabUmbra card) {
        super(card);
    }

    @Override
    public CrabUmbra copy() {
        return new CrabUmbra(this);
    }
}
