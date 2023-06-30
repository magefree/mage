package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.ControllerPlaysLandTriggeredAbility;
import mage.abilities.common.OpponentPlaysLandTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
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

/**
 *
 * @author jeffwadsworth
 */
public final class HiddenStag extends CardImpl {

    public HiddenStag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever an opponent plays a land, if Hidden Stag is an enchantment, Hidden Stag becomes a 3/2 Elk Beast creature.
        Effect effect = new BecomesCreatureSourceEffect(new ElkBeastToken(), null, Duration.WhileOnBattlefield);
        TriggeredAbility ability = new OpponentPlaysLandTriggeredAbility(Zone.BATTLEFIELD, effect, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "Whenever an opponent plays a land, if Hidden Stag is an enchantment, Hidden Stag becomes a 3/2 Elk Beast creature."));

        // Whenever you play a land, if Hidden Stag is a creature, Hidden Stag becomes an enchantment.
        Effect effect2 = new BecomesEnchantmentSourceEffect();
        TriggeredAbility ability2 = new ControllerPlaysLandTriggeredAbility(Zone.BATTLEFIELD, effect2, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability2, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE),
                "Whenever you play a land, if Hidden Stag is a creature, Hidden Stag becomes an enchantment."));

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

    public ElkBeastToken(final ElkBeastToken token) {
        super(token);
    }

    public ElkBeastToken copy() {
        return new ElkBeastToken(this);
    }
}
