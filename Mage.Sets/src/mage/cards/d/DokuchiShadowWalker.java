package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DokuchiShadowWalker extends CardImpl {

    public DokuchiShadowWalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Ninjutsu {3}{B}
        this.addAbility(new NinjutsuAbility("{3}{B}"));
    }

    private DokuchiShadowWalker(final DokuchiShadowWalker card) {
        super(card);
    }

    @Override
    public DokuchiShadowWalker copy() {
        return new DokuchiShadowWalker(this);
    }
}
