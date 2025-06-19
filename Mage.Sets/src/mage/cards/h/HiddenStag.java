package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.ControllerPlaysLandTriggeredAbility;
import mage.abilities.common.OpponentPlaysLandTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BecomesEnchantmentSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HiddenStag extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(
            "this permanent is a creature", StaticFilters.FILTER_PERMANENT_CREATURE
    );

    public HiddenStag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever an opponent plays a land, if Hidden Stag is an enchantment, Hidden Stag becomes a 3/2 Elk Beast creature.
        this.addAbility(new OpponentPlaysLandTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        3, 2, "3/2 Elk Beast creature", SubType.ELK, SubType.BEAST
                ), null, Duration.WhileOnBattlefield
        ), false).withInterveningIf(SourceIsEnchantmentCondition.instance).withRuleTextReplacement(true));

        // Whenever you play a land, if Hidden Stag is a creature, Hidden Stag becomes an enchantment.
        this.addAbility(new ControllerPlaysLandTriggeredAbility(
                Zone.BATTLEFIELD, new BecomesEnchantmentSourceEffect(), false
        ).withInterveningIf(condition).withRuleTextReplacement(true));
    }

    private HiddenStag(final HiddenStag card) {
        super(card);
    }

    @Override
    public HiddenStag copy() {
        return new HiddenStag(this);
    }
}

class ElkBeastToken extends TokenImpl {

    public ElkBeastToken() {
        super("Elk Beast", "3/2 Elk Beast creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELK);
        subtype.add(SubType.BEAST);
        power = new MageInt(3);
        toughness = new MageInt(2);
    }

    private ElkBeastToken(final ElkBeastToken token) {
        super(token);
    }

    public ElkBeastToken copy() {
        return new ElkBeastToken(this);
    }
}
