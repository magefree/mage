package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Gumato extends CardImpl {

    public Gumato(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Add {G}{G}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(3), new TapSourceCost()));
    }

    private Gumato(final Gumato card) {
        super(card);
    }

    @Override
    public Gumato copy() {
        return new Gumato(this);
    }
}
