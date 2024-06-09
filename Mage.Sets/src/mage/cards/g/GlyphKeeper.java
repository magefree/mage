package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceFirstTimeTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Styxo, xenohedron
 */
public final class GlyphKeeper extends CardImpl {

    public GlyphKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Glyph Keeper becomes the target of a spell or ability for the first time each turn, counter that spell or ability.
        this.addAbility(new BecomesTargetSourceFirstTimeTriggeredAbility(
                new CounterTargetEffect().setText("counter that spell or ability"),
                StaticFilters.FILTER_SPELL_OR_ABILITY_A, SetTargetPointer.SPELL, false
        ));

        // Embalm {5}{U}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{5}{U}{U}"), this));

    }

    private GlyphKeeper(final GlyphKeeper card) {
        super(card);
    }

    @Override
    public GlyphKeeper copy() {
        return new GlyphKeeper(this);
    }
}
