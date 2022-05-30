
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author JRHerlehy Created on 4/7/18.
 */
public final class DrudgeSentinel extends CardImpl {

    public DrudgeSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.SKELETON, SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {3}: Tap Drudge Sentinel. It gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new TapSourceEffect(), new ManaCostsImpl<>("{3}"));
        ability.addEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)
                .setText("It gains indestructible until end of turn"));
        this.addAbility(ability);
    }

    private DrudgeSentinel(final DrudgeSentinel card) {
        super(card);
    }

    @Override
    public DrudgeSentinel copy() {
        return new DrudgeSentinel(this);
    }
}
