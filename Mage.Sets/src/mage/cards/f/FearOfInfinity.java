package mage.cards.f;

import mage.MageInt;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfInfinity extends CardImpl {

    public FearOfInfinity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Fear of Infinity can't block.
        this.addAbility(new CantBlockAbility());

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, you may return Fear of Infinity from your graveyard to your hand.
        this.addAbility(new EerieAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), true));
    }

    private FearOfInfinity(final FearOfInfinity card) {
        super(card);
    }

    @Override
    public FearOfInfinity copy() {
        return new FearOfInfinity(this);
    }
}
