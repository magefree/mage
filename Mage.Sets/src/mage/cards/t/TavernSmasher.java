package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TavernSmasher extends CardImpl {

    public TavernSmasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        this.nightCard = true;

        // Nightbound
        this.addAbility(NightboundAbility.getInstance());
        this.addAbility(new TransformAbility());
    }

    private TavernSmasher(final TavernSmasher card) {
        super(card);
    }

    @Override
    public TavernSmasher copy() {
        return new TavernSmasher(this);
    }
}
