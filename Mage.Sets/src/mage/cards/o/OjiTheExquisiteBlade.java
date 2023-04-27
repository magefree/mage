package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OjiTheExquisiteBlade extends CardImpl {

    public OjiTheExquisiteBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Oji, the Exquisite Blade enters the battlefield, you gain 2 life and scry 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2));
        ability.addEffect(new ScryEffect(2, false).concatBy("and"));
        this.addAbility(ability);

        // Whenever you cast your second spell each turn, exile up to one target creature you control, then return it to the battlefield under its owner's control.
        ability = new CastSecondSpellTriggeredAbility(new ExileTargetForSourceEffect());
        ability.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false, "it").concatBy(", then"));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private OjiTheExquisiteBlade(final OjiTheExquisiteBlade card) {
        super(card);
    }

    @Override
    public OjiTheExquisiteBlade copy() {
        return new OjiTheExquisiteBlade(this);
    }
}
