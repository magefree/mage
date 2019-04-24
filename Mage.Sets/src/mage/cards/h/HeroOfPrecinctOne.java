package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeroOfPrecinctOne extends CardImpl {

    public HeroOfPrecinctOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a multicolored spell, create a 1/1 white Human creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new HumanToken()), StaticFilters.FILTER_SPELL_A_MULTICOLORED, false
        ));
    }

    private HeroOfPrecinctOne(final HeroOfPrecinctOne card) {
        super(card);
    }

    @Override
    public HeroOfPrecinctOne copy() {
        return new HeroOfPrecinctOne(this);
    }
}
