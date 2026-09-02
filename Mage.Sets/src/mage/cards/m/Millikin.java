
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
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
        final SimpleActivatedAbility ability = new SimpleActivatedAbility(new BasicManaEffect(Mana.ColorlessMana(1)), new TapSourceCost());
        ability.addCost(new MillCardsCost());
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
