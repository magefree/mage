package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SourceDealsDamageToYouTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FlamebladeAngel extends CardImpl {

    public FlamebladeAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a source an opponent controls deals damage to you or a permanent you control, you may have Flameblade Angel deal 1 damage to that source's controller.
        Effect effect = new DamageTargetEffect(1, true, "that source's controller");
        this.addAbility(new SourceDealsDamageToYouTriggeredAbility(effect, StaticFilters.FILTER_PERMANENT, true));

    }

    private FlamebladeAngel(final FlamebladeAngel card) {
        super(card);
    }

    @Override
    public FlamebladeAngel copy() {
        return new FlamebladeAngel(this);
    }
}
