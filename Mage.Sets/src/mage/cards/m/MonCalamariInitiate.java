
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class MonCalamariInitiate extends CardImpl {

    public MonCalamariInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.CALAMARI);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Mon Calamari Initiate enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Meditate {1}{U}
        this.addAbility(new MeditateAbility(new ManaCostsImpl<>("{1}{U}")));
    }

    private MonCalamariInitiate(final MonCalamariInitiate card) {
        super(card);
    }

    @Override
    public MonCalamariInitiate copy() {
        return new MonCalamariInitiate(this);
    }
}
