package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class RosemaneCentaur extends CardImpl {

    public RosemaneCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private RosemaneCentaur(final RosemaneCentaur card) {
        super(card);
    }

    @Override
    public RosemaneCentaur copy() {
        return new RosemaneCentaur(this);
    }
}
