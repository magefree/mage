
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author LevelX2
 */
public final class MeletisAstronomer extends CardImpl {

    private static final FilterCard filter = new FilterCard("an enchantment card");
    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }
    
    public MeletisAstronomer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Meletis Astronomer, look at the top three cards of your library. You may reveal an enchantment card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new HeroicAbility(new LookLibraryAndPickControllerEffect(3, 1, filter, true, false, Zone.HAND, true), false));
    }

    private MeletisAstronomer(final MeletisAstronomer card) {
        super(card);
    }

    @Override
    public MeletisAstronomer copy() {
        return new MeletisAstronomer(this);
    }
}
