package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.GreenElfToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ThranduilTheStrategist extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "Elves you control");


    public ThranduilTheStrategist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other Elves you control have "{T}: Add {G} or {U}."
        Ability ability = new SimpleStaticAbility(
            new GainAbilityControlledEffect(new GreenManaAbility(), Duration.WhileOnBattlefield, filter, true)
                .setText("Other Elves you control have \"{T}: Add {G} or {U}.\"")
        );
        ability.addEffect(
            new GainAbilityControlledEffect(new BlueManaAbility(), Duration.WhileOnBattlefield, filter, true)
                .setText("")
        );
        this.addAbility(ability);

        // Landfall -- Whenever a land you control enters, create a 1/1 green Elf creature token.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new GreenElfToken())));
    }

    private ThranduilTheStrategist(final ThranduilTheStrategist card) {
        super(card);
    }

    @Override
    public ThranduilTheStrategist copy() {
        return new ThranduilTheStrategist(this);
    }
}
