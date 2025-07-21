package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MageRingResponder extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature defending player controls");

    public MageRingResponder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Mage-Ring Responder doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepSourceEffect()));

        // {7}: Untap Mage-Ring Responder.
        this.addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new ManaCostsImpl<>("{7}")));

        // Whenever Mage-Ring Responder attacks, it deals 7 damage to target creature defending player controls.
        Ability ability = new AttacksTriggeredAbility(new DamageTargetEffect(7), false, null, SetTargetPointer.PLAYER);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private MageRingResponder(final MageRingResponder card) {
        super(card);
    }

    @Override
    public MageRingResponder copy() {
        return new MageRingResponder(this);
    }
}
