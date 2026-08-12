package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class GreatGoblinFoulHearted extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.ARMY, "Armies");

    public GreatGoblinFoulHearted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Great Goblin enters or attacks, amass Goblins 3.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new AmassEffect(3, SubType.GOBLIN)
        ));

        // Armies you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private GreatGoblinFoulHearted(final GreatGoblinFoulHearted card) {
        super(card);
    }

    @Override
    public GreatGoblinFoulHearted copy() {
        return new GreatGoblinFoulHearted(this);
    }
}
