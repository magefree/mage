
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX
 */
public final class KitsuneRiftwalker extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spirits and from Arcane");

    static {
        filter.add(Predicates.or(SubType.ARCANE.getPredicate(), SubType.SPIRIT.getPredicate()));
    }

    public KitsuneRiftwalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from Spirits and from Arcane
        this.addAbility(new ProtectionAbility(filter));
    }

    private KitsuneRiftwalker(final KitsuneRiftwalker card) {
        super(card);
    }

    @Override
    public KitsuneRiftwalker copy() {
        return new KitsuneRiftwalker(this);
    }
}
