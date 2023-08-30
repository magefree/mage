package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LuminousPhantom extends CardImpl {

    public LuminousPhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another creature you control leaves the battlefield, you gain 1 life.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(new GainLifeEffect(1), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL));

        // If Luminous Phantom would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new ExileSourceEffect().setText("exile it instead")));
    }

    private LuminousPhantom(final LuminousPhantom card) {
        super(card);
    }

    @Override
    public LuminousPhantom copy() {
        return new LuminousPhantom(this);
    }
}
