package mage.cards.l;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeonardoWorldlyWarrior extends CardImpl {

    public LeonardoWorldlyWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast for each creature you control.
        this.addAbility(new AffinityAbility(AffinityType.CREATURES));

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private LeonardoWorldlyWarrior(final LeonardoWorldlyWarrior card) {
        super(card);
    }

    @Override
    public LeonardoWorldlyWarrior copy() {
        return new LeonardoWorldlyWarrior(this);
    }
}
