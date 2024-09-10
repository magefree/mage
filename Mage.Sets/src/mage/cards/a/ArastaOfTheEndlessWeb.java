package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArastaOfTheEndlessWeb extends CardImpl {

    public ArastaOfTheEndlessWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever an opponent casts an instant or sorcery spell, create a 1/2 green Spider creature token with reach.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new CreateTokenEffect(new SpiderToken()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private ArastaOfTheEndlessWeb(final ArastaOfTheEndlessWeb card) {
        super(card);
    }

    @Override
    public ArastaOfTheEndlessWeb copy() {
        return new ArastaOfTheEndlessWeb(this);
    }
}
