package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class BloodswornKnight extends CardImpl {

    public BloodswornKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setBlack(true);

        // Back half of Bloodsworn Squire
        this.nightCard = true;

        // Bloodsworn Knight's power and toughness are each equal to the number of creature cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new SetPowerToughnessSourceEffect(new CardsInControllerGraveyardCount(), Duration.EndOfGame)));

        // {1}{B}, Discard a card: Bloodsworn Knight gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new DiscardCardCost());
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        this.addAbility(ability);
    }

    private BloodswornKnight(final BloodswornKnight card) {
        super(card);
    }

    @Override
    public BloodswornKnight copy() {
        return new BloodswornKnight(this);
    }
}
