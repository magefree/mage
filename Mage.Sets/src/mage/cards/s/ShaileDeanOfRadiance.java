package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class ShaileDeanOfRadiance extends CardImpl {

    public ShaileDeanOfRadiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Put a +1/+1 counter on each creature that entered the battlefield under your control this turn.
    }

    private ShaileDeanOfRadiance(final ShaileDeanOfRadiance card) {
        super(card);
    }

    @Override
    public ShaileDeanOfRadiance copy() {
        return new ShaileDeanOfRadiance(this);
    }
}
