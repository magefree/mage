
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 * @author nantuko
 */
public final class EliteInquisitor extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("Vampires, from Werewolves, and from Zombies");

    static {filter.add(Predicates.or(
            SubType.VAMPIRE.getPredicate(),
            SubType.WEREWOLF.getPredicate(),
            SubType.ZOMBIE.getPredicate()
            ));
    }

    public EliteInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());

        // Protection from Vampires, from Werewolves, and from Zombies.
        this.addAbility(new ProtectionAbility(filter));
    }

    private EliteInquisitor(final EliteInquisitor card) {
        super(card);
    }

    @Override
    public EliteInquisitor copy() {
        return new EliteInquisitor(this);
    }
}
