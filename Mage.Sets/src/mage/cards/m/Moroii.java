
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class Moroii extends CardImpl {

    public Moroii(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your upkeep, you lose 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LoseLifeSourceControllerEffect(1)));
    }

    private Moroii(final Moroii card) {
        super(card);
    }

    @Override
    public Moroii copy() {
        return new Moroii(this);
    }
}
