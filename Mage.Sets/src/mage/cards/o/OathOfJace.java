
package mage.cards.o;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OathOfJace extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent("planeswalkers you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Planeswalkers you control", xValue);

    public OathOfJace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.supertype.add(SuperType.LEGENDARY);

        // When Oath of Jace enters the battlefield, draw three cards, then discard two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(3, 2), false));

        // At the beginning of your upkeep, scry X, where X is the number of planeswalkers you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ScryEffect(xValue)).addHint(hint));
    }

    private OathOfJace(final OathOfJace card) {
        super(card);
    }

    @Override
    public OathOfJace copy() {
        return new OathOfJace(this);
    }
}