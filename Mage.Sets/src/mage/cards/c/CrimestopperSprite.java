package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CollectedEvidenceCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CollectEvidenceAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrimestopperSprite extends CardImpl {

    public CrimestopperSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As an additional cost to cast this spell, you may collect evidence 6.
        this.addAbility(new CollectEvidenceAbility(6));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Crimestopper Sprite enters the battlefield, tap target creature. If evidence was collected, put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.STUN.createInstance()),
                CollectedEvidenceCondition.instance, "If evidence was collected, put a stun counter on it"
        ));
        this.addAbility(ability);
    }

    private CrimestopperSprite(final CrimestopperSprite card) {
        super(card);
    }

    @Override
    public CrimestopperSprite copy() {
        return new CrimestopperSprite(this);
    }
}
