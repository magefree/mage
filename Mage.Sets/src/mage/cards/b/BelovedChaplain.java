
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

/**
 *
 * @author cbt33, BetaSteward (Black Knight)
 */
public final class BelovedChaplain extends CardImpl {

    static final FilterCard filter = new FilterCard("creatures");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public BelovedChaplain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN, SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from creatures
        this.addAbility(new ProtectionAbility(filter));
    }

    private BelovedChaplain(final BelovedChaplain card) {
        super(card);
    }

    @Override
    public BelovedChaplain copy() {
        return new BelovedChaplain(this);
    }
}
