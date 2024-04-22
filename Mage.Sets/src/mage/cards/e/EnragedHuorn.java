package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnragedHuorn extends CardImpl {

    public EnragedHuorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Enraged Huorn enters the battlefield, the Ring tempts you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TheRingTemptsYouEffect()));
    }

    private EnragedHuorn(final EnragedHuorn card) {
        super(card);
    }

    @Override
    public EnragedHuorn copy() {
        return new EnragedHuorn(this);
    }
}
