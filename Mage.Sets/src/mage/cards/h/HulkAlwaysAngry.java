package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HulkAlwaysAngry extends CardImpl {

    public HulkAlwaysAngry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Hulk enters, destroy all artifacts.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS)
        ));

        // Hulk attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private HulkAlwaysAngry(final HulkAlwaysAngry card) {
        super(card);
    }

    @Override
    public HulkAlwaysAngry copy() {
        return new HulkAlwaysAngry(this);
    }
}
