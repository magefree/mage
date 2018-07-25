package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Thor extends CardImpl {

    public Thor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {2}{W}, {T}: Thor deals 3 damage to target attacking or blocking creature.
    }

    public Thor(final Thor card) {
        super(card);
    }

    @Override
    public Thor copy() {
        return new Thor(this);
    }
}
