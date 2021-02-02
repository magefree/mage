
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class MoongloveChangeling extends CardImpl {

    public MoongloveChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new ChangelingAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.B)));
    }

    private MoongloveChangeling(final MoongloveChangeling card) {
        super(card);
    }

    @Override
    public MoongloveChangeling copy() {
        return new MoongloveChangeling(this);
    }
}
