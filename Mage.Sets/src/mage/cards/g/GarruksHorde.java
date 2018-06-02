
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 * @author nantuko
 */
public final class GarruksHorde extends CardImpl {

    public GarruksHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        this.addAbility(TrampleAbility.getInstance());
        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
        // You may cast the top card of your library if it's a creature card.
        Effect effect = new PlayTheTopCardEffect(new FilterCreatureCard());
        effect.setText("You may cast the top card of your library if it's a creature card");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public GarruksHorde(final GarruksHorde card) {
        super(card);
    }

    @Override
    public GarruksHorde copy() {
        return new GarruksHorde(this);
    }
}
