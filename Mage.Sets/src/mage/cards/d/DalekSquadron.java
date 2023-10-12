package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DalekSquadron extends CardImpl {

    public DalekSquadron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.DALEK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Myriad
        this.addAbility(new MyriadAbility());
    }

    private DalekSquadron(final DalekSquadron card) {
        super(card);
    }

    @Override
    public DalekSquadron copy() {
        return new DalekSquadron(this);
    }
}
