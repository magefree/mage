package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SallySparrow extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature spells");

    public SallySparrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may cast creature spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)));

        // Whenever one or more other creatures you control leave the battlefield, investigate. This ability triggers only once each turn.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new InvestigateEffect(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES
        ).setTriggersOnceEachTurn(true)
                .setTriggerPhrase("Whenever one or more other creatures you control leave the battlefield, "));
    }

    private SallySparrow(final SallySparrow card) {
        super(card);
    }

    @Override
    public SallySparrow copy() {
        return new SallySparrow(this);
    }
}
