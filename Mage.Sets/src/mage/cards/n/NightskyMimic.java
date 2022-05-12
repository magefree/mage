
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SubLayer;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class NightskyMimic extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that's both black and green");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    private static final String rule = "Whenever you cast a spell that's both white and black, {this} has base power and toughness 4/4 until end of turn and gains flying until end of turn.";

    public NightskyMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell that's both white and black, Nightsky Mimic has base power and toughness 4/4 until end of turn and gains flying until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new SetPowerToughnessSourceEffect(4, 4, Duration.EndOfTurn, SubLayer.SetPT_7b), filter, false, rule);
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, false, true));
        this.addAbility(ability);
    }

    private NightskyMimic(final NightskyMimic card) {
        super(card);
    }

    @Override
    public NightskyMimic copy() {
        return new NightskyMimic(this);
    }
}
