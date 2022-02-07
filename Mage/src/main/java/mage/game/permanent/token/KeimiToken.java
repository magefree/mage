package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class KeimiToken extends TokenImpl {

    public KeimiToken() {
        super("Keimi", "Keimi, a legendary 3/3 black and green Frog creature token with \"Whenever you cast an enchantment spell, each opponent loses 1 life and you gain 1 life.\"");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.FROG);
        power = new MageInt(3);
        toughness = new MageInt(3);

        Ability ability = new SpellCastControllerTriggeredAbility(
                new LoseLifeOpponentsEffect(1),
                StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        availableImageSetCodes = Arrays.asList("NEO");
    }

    private KeimiToken(final KeimiToken token) {
        super(token);
    }

    public KeimiToken copy() {
        return new KeimiToken(this);
    }
}
