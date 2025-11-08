package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SokkaTenaciousTactician extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ALLY, "Allies");

    public SokkaTenaciousTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Other Allies you control have menace and prowess.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new ProwessAbility(), Duration.WhileOnBattlefield, filter, true
        ).setText("and prowess"));
        this.addAbility(ability);

        // Whenever you cast a noncreature spell, create a 1/1 white Ally creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new AllyToken()), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private SokkaTenaciousTactician(final SokkaTenaciousTactician card) {
        super(card);
    }

    @Override
    public SokkaTenaciousTactician copy() {
        return new SokkaTenaciousTactician(this);
    }
}
