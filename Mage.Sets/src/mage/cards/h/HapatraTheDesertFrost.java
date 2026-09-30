package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HapatraTheDesertFrost extends CardImpl {

    public HapatraTheDesertFrost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Hapatra enters, for each opponent, tap up to one target creature that player controls. Put a stun counter on each of those creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new TapTargetEffect().setText("for each opponent, tap up to one target creature that player controls")
        );
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("Put a stun counter on each of those creatures"));
        ability.getEffects().setTargetPointer(new EachTargetPointer());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);

        // {2}{U}: Untap target creature.
        Ability ability2 = new SimpleActivatedAbility(new UntapTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    private HapatraTheDesertFrost(final HapatraTheDesertFrost card) {
        super(card);
    }

    @Override
    public HapatraTheDesertFrost copy() {
        return new HapatraTheDesertFrost(this);
    }
}
