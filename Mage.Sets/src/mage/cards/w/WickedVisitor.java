package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WickedVisitor extends CardImpl {

    public WickedVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an enchantment you control is put into a graveyard from the battlefield, each opponent loses 1 life.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_PERMANENT_AN_ENCHANTMENT, false
        ));
    }

    private WickedVisitor(final WickedVisitor card) {
        super(card);
    }

    @Override
    public WickedVisitor copy() {
        return new WickedVisitor(this);
    }
}
