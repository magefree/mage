package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class PoisonTipArcher extends CardImpl {

    public PoisonTipArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever another creature dies, each opponent loses 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false, true
        ));
    }

    private PoisonTipArcher(final PoisonTipArcher card) {
        super(card);
    }

    @Override
    public PoisonTipArcher copy() {
        return new PoisonTipArcher(this);
    }
}
