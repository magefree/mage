

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class NobleHierarch extends CardImpl {

    public NobleHierarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        this.addAbility(new ExaltedAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private NobleHierarch(final NobleHierarch card) {
        super(card);
    }

    @Override
    public NobleHierarch copy() {
        return new NobleHierarch(this);
    }
}
