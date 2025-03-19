package mage.cards.q;

import mage.MageInt;
import mage.abilities.common.RenewAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QarsiRevenant extends CardImpl {

    public QarsiRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Renew -- {2}{B}, Exile this card from your graveyard: Put a flying counter, a deathtouch counter, and a lifelink counter on target creature. Activate only as a sorcery.
        this.addAbility(new RenewAbility(
                "{2}{B}",
                CounterType.FLYING.createInstance(),
                CounterType.DEATHTOUCH.createInstance(),
                CounterType.LIFELINK.createInstance()
        ));
    }

    private QarsiRevenant(final QarsiRevenant card) {
        super(card);
    }

    @Override
    public QarsiRevenant copy() {
        return new QarsiRevenant(this);
    }
}
