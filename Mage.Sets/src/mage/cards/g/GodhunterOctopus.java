
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnchantedPredicate;

/**
 *
 * @author LevelX2
 */
public final class GodhunterOctopus extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an enchantment or an enchanted permanent");

    static {
        filter.add(Predicates.or(
                CardType.ENCHANTMENT.getPredicate(),
                EnchantedPredicate.instance));
    }

    public GodhunterOctopus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.OCTOPUS);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Godhunter Octopus can't attack unless defending player controls an enchantment or an enchanted permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(filter)));
    }

    private GodhunterOctopus(final GodhunterOctopus card) {
        super(card);
    }

    @Override
    public GodhunterOctopus copy() {
        return new GodhunterOctopus(this);
    }
}
