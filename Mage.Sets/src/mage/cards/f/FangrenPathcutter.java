package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class FangrenPathcutter extends CardImpl {

    public FangrenPathcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Whenever Fangren Pathcutter attacks, attacking creatures gain trample until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES), false));
    }

    private FangrenPathcutter(final FangrenPathcutter card) {
        super(card);
    }

    @Override
    public FangrenPathcutter copy() {
        return new FangrenPathcutter(this);
    }
}
