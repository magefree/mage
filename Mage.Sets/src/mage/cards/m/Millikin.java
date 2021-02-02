
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class Millikin extends CardImpl {

    public Millikin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {tap}, Put the top card of your library into your graveyard: Add {C}.
        ColorlessManaAbility ability = new ColorlessManaAbility();
        ability.addCost(new MillCardsCost());
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    private Millikin(final Millikin card) {
        super(card);
    }

    @Override
    public Millikin copy() {
        return new Millikin(this);
    }
}
