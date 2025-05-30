package mage.cards.o;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KorAllyToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OathOfGideon extends CardImpl {

    public OathOfGideon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.supertype.add(SuperType.LEGENDARY);

        // When Oath of Gideon enters the battlefield, create two 1/1 Kor Ally creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KorAllyToken(), 2), false));

        // Each planeswalker you control enters the battlefield with an additional loyalty counter on it.
        this.addAbility(new SimpleStaticAbility(new EntersWithCountersControlledEffect(
                StaticFilters.FILTER_PERMANENT_PLANESWALKER,
                CounterType.LOYALTY.createInstance(), false
        )));
    }

    private OathOfGideon(final OathOfGideon card) {
        super(card);
    }

    @Override
    public OathOfGideon copy() {
        return new OathOfGideon(this);
    }
}
