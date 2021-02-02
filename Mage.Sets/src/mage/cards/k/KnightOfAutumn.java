package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class KnightOfAutumn extends CardImpl {

    public KnightOfAutumn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Knight of Autumn enters the battlefield, choose one —
        // • Put two +1/+1 counters on Knight of Autumn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(2)
                ), false
        );

        // • Destroy target artifact or enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(
                StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT
        ));
        ability.addMode(mode);

        // • You gain 4 life.
        ability.addMode(new Mode(new GainLifeEffect(4)));
        this.addAbility(ability);
    }

    private KnightOfAutumn(final KnightOfAutumn card) {
        super(card);
    }

    @Override
    public KnightOfAutumn copy() {
        return new KnightOfAutumn(this);
    }
}
