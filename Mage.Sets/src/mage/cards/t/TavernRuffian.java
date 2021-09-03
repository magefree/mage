package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TavernRuffian extends CardImpl {

    public TavernRuffian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.t.TavernSmasher.class;

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(DayboundAbility.getInstance());
    }

    private TavernRuffian(final TavernRuffian card) {
        super(card);
    }

    @Override
    public TavernRuffian copy() {
        return new TavernRuffian(this);
    }
}
