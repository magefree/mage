package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArgivianPhalanx extends CardImpl {

    public ArgivianPhalanx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Affinity for creatures
        this.addAbility(new AffinityAbility(AffinityType.CREATURES));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private ArgivianPhalanx(final ArgivianPhalanx card) {
        super(card);
    }

    @Override
    public ArgivianPhalanx copy() {
        return new ArgivianPhalanx(this);
    }
}
