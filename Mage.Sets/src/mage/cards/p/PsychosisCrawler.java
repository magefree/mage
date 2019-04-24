
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class PsychosisCrawler extends CardImpl {

    public PsychosisCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new CardsInControllerHandCount(), Duration.EndOfGame)));
        this.addAbility(new DrawCardControllerTriggeredAbility(new LoseLifeOpponentsEffect(1), false));
    }

    public PsychosisCrawler(final PsychosisCrawler card) {
        super(card);
    }

    @Override
    public PsychosisCrawler copy() {
        return new PsychosisCrawler(this);
    }
}
