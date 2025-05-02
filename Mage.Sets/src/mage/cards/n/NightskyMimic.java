package mage.cards.n;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NightskyMimic extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that's both white and black");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public NightskyMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell that's both white and black, Nightsky Mimic has base power and toughness 4/4 until end of turn and gains flying until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new SetBasePowerToughnessSourceEffect(4, 4, Duration.EndOfTurn),
                filter, false
        );
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains flying until end of turn"));
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
