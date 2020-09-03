package mage.cards.t;

import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TangledFlorahedron extends CardImpl {

    public TangledFlorahedron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.t.TangledVale.class;

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private TangledFlorahedron(final TangledFlorahedron card) {
        super(card);
    }

    @Override
    public TangledFlorahedron copy() {
        return new TangledFlorahedron(this);
    }
}
