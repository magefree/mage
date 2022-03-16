package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.DashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KolaghanForerunners extends CardImpl {

    public KolaghanForerunners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Kolaghan Forerunners' power is equal to the number of creatures you control.
        Effect effect = new SetPowerSourceEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURES), Duration.EndOfGame);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect).addHint(CreaturesYouControlHint.instance));

        // Dash {2}{R} <i.(You may cast this spell for its dash cost. If you do it gains haste and it's returned to its owner's hand at the beginning of the next end step.)</i>
        this.addAbility(new DashAbility(this, "{2}{R}"));
    }

    private KolaghanForerunners(final KolaghanForerunners card) {
        super(card);
    }

    @Override
    public KolaghanForerunners copy() {
        return new KolaghanForerunners(this);
    }
}
