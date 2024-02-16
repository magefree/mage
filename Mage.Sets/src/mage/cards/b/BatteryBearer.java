package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BatteryBearer extends CardImpl {

    private static final FilterSpell filter
            = new FilterArtifactSpell("an artifact spell with mana value 6 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 5));
    }

    public BatteryBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Creatures you control have "{T}: Add {C}. This mana can't be spent to cast a nonartifact spell."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ConditionalColorlessManaAbility(1, PowerstoneToken.makeBuilder()),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // Whenever you cast an artifact spell with mana value 6 or greater, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ));
    }

    private BatteryBearer(final BatteryBearer card) {
        super(card);
    }

    @Override
    public BatteryBearer copy() {
        return new BatteryBearer(this);
    }
}
