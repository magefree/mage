
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class SkitteringHeartstopper extends CardImpl {

    public SkitteringHeartstopper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {B}: Skittering Heartstopper gains deathtouch until end of turn. (Any amount of damage it deals to a creature is enough to destroy it.)
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.B)));
    }

    private SkitteringHeartstopper(final SkitteringHeartstopper card) {
        super(card);
    }

    @Override
    public SkitteringHeartstopper copy() {
        return new SkitteringHeartstopper(this);
    }
}
