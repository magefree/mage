package mage.cards.w;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardAtRandomEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class WoodlandSleuth extends CardImpl {

    private static final String staticText = "<i>Morbid</i> &mdash; When {this} enters the battlefield, if a creature died this turn, return a creature card at random from your graveyard to your hand.";

    public WoodlandSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // <i>Morbid</i> &mdash; When Woodland Sleuth enters the battlefield, if a creature died this turn, return a creature card at random from your graveyard to your hand.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(
                new ReturnFromGraveyardAtRandomEffect(StaticFilters.FILTER_CARD_CREATURE, Zone.HAND));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, MorbidCondition.instance, staticText).addHint(MorbidHint.instance));
    }

    private WoodlandSleuth(final WoodlandSleuth card) {
        super(card);
    }

    @Override
    public WoodlandSleuth copy() {
        return new WoodlandSleuth(this);
    }
}
