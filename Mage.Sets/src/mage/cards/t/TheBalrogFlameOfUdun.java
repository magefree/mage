package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBalrogFlameOfUdun extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public TheBalrogFlameOfUdun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When a legendary creature an opponent controls dies, put The Balrog, Flame of Udun on the bottom of its owner's library.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new PutOnLibrarySourceEffect(false), false, filter
        ).setTriggerPhrase("When a legendary creature an opponent controls dies, "));
    }

    private TheBalrogFlameOfUdun(final TheBalrogFlameOfUdun card) {
        super(card);
    }

    @Override
    public TheBalrogFlameOfUdun copy() {
        return new TheBalrogFlameOfUdun(this);
    }
}
