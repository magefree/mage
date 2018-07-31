package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.YoungPyromancerElementalToken;

/**
 *
 * @author jeffwadsworth
 */
public final class YoungPyromancer extends CardImpl {

    public YoungPyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast an instant or sorcery spell, create a 1/1 red Elemental creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new YoungPyromancerElementalToken()),
                StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY, false
        ));

    }

    public YoungPyromancer(final YoungPyromancer card) {
        super(card);
    }

    @Override
    public YoungPyromancer copy() {
        return new YoungPyromancer(this);
    }

}
