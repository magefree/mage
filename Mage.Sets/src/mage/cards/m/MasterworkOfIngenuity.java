package mage.cards.m;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MasterworkOfIngenuity extends CardImpl {

    public MasterworkOfIngenuity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // You may have Masterwork of Ingenuity enter the battlefield as a copy of any Equipment on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_EQUIPMENT), true));
    }

    private MasterworkOfIngenuity(final MasterworkOfIngenuity card) {
        super(card);
    }

    @Override
    public MasterworkOfIngenuity copy() {
        return new MasterworkOfIngenuity(this);
    }
}
