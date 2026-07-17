package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronShieldElf extends CardImpl {

    public IronShieldElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Discard a card: This creature gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new DiscardCardCost());
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        this.addAbility(ability);
    }

    private IronShieldElf(final IronShieldElf card) {
        super(card);
    }

    @Override
    public IronShieldElf copy() {
        return new IronShieldElf(this);
    }
}
