
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class ShorecrasherMimic extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that's both green and blue");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    private static final String rule = "Whenever you cast a spell that's both green and blue, {this} has base power and toughness 5/3 until end of turn and gains trample until end of turn.";

    public ShorecrasherMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell that's both green and blue, Shorecrasher Mimic has base power and toughness 5/3 until end of turn and gains trample until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new SetBasePowerToughnessSourceEffect(5, 3, Duration.EndOfTurn, SubLayer.SetPT_7b, true),
                filter,
                false,
                rule);
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, false, true));
        this.addAbility(ability);

    }

    private ShorecrasherMimic(final ShorecrasherMimic card) {
        super(card);
    }

    @Override
    public ShorecrasherMimic copy() {
        return new ShorecrasherMimic(this);
    }
}
