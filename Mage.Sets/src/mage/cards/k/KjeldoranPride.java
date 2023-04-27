
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherEnchantedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Ketsuban
 */
public final class KjeldoranPride extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature other than enchanted creature");

    static {
        filter.add(new AnotherEnchantedPredicate());
    }

    public KjeldoranPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ENCHANTMENT }, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability enchantAbility = new EnchantAbility(auraTarget);
        this.addAbility(enchantAbility);

        // Enchanted creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 2)));

        // 2U: Attach Kjeldoran Pride to target creature other than enchanted creature.
        Ability ability = new SimpleActivatedAbility(new AttachEffect(Outcome.Benefit), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private KjeldoranPride(final KjeldoranPride card) {
        super(card);
    }

    @Override
    public KjeldoranPride copy() {
        return new KjeldoranPride(this);
    }
}
