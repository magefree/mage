
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class WretchedGryff extends CardImpl {

    public WretchedGryff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.HIPPOGRIFF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Emerge {5}{U} (You may cast this spell by sacrificing a creature and paying the emerge cost reduced by that creature's converted mana cost.)
        this.addAbility(new EmergeAbility(this, new ManaCostsImpl<>("{5}{U}")));

        // When you cast Wretched Gryff, draw a card.
        this.addAbility(new CastSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private WretchedGryff(final WretchedGryff card) {
        super(card);
    }

    @Override
    public WretchedGryff copy() {
        return new WretchedGryff(this);
    }
}
