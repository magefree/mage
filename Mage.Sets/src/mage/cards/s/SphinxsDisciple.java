
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SphinxsDisciple extends CardImpl {

    public SphinxsDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Inspired</i> &mdash; Whenever Sphinx's Disciple becomes untapped, draw a card.
        this.addAbility(new InspiredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private SphinxsDisciple(final SphinxsDisciple card) {
        super(card);
    }

    @Override
    public SphinxsDisciple copy() {
        return new SphinxsDisciple(this);
    }
}
