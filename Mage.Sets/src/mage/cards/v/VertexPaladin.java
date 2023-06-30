package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VertexPaladin extends CardImpl {

    public VertexPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vertex Paladin's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(CreaturesYouControlCount.instance)));
    }

    private VertexPaladin(final VertexPaladin card) {
        super(card);
    }

    @Override
    public VertexPaladin copy() {
        return new VertexPaladin(this);
    }
}
