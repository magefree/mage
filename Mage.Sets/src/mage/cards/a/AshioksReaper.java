package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshioksReaper extends CardImpl {

    public AshioksReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an enchantment you control is put into a graveyard from the battlefield, draw a card.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_PERMANENT_AN_ENCHANTMENT, false
        ));
    }

    private AshioksReaper(final AshioksReaper card) {
        super(card);
    }

    @Override
    public AshioksReaper copy() {
        return new AshioksReaper(this);
    }
}
