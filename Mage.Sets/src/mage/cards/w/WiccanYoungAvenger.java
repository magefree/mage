package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WiccanYoungAvenger extends CardImpl {

    public WiccanYoungAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WARLOCK);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, exile the top card of your library. Until your next end step, you may play that card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new ExileTopXMayPlayUntilEffect(1, Duration.UntilYourNextEndStep)
                .withTextOptions("that card", false),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private WiccanYoungAvenger(final WiccanYoungAvenger card) {
        super(card);
    }

    @Override
    public WiccanYoungAvenger copy() {
        return new WiccanYoungAvenger(this);
    }
}
