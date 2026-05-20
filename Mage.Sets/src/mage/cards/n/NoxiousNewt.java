package mage.cards.n;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoxiousNewt extends CardImpl {

    public NoxiousNewt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SALAMANDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private NoxiousNewt(final NoxiousNewt card) {
        super(card);
    }

    @Override
    public NoxiousNewt copy() {
        return new NoxiousNewt(this);
    }
}
