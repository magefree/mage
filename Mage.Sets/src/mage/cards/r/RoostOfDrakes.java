package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DrakeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoostOfDrakes extends CardImpl {

    public RoostOfDrakes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // Kicker {2}{U}
        this.addAbility(new KickerAbility("{2}{U}"));

        // When Roost of Drakes enters the battlefield, if it was kicked, create a 2/2 blue Drake creature token with flying.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new DrakeToken())),
                KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, " +
                "create a 2/2 blue Drake creature token with flying."
        ));

        // Whenever you cast a kicked spell, create a 2/2 blue Drake creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new DrakeToken()), StaticFilters.FILTER_SPELL_KICKED_A, false
        ));
    }

    private RoostOfDrakes(final RoostOfDrakes card) {
        super(card);
    }

    @Override
    public RoostOfDrakes copy() {
        return new RoostOfDrakes(this);
    }
}
