package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GurmagRakshasa extends CardImpl {

    public GurmagRakshasa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility());

        // When this creature enters, target creature an opponent controls gets -2/-2 until end of turn and target creature you control gets +2/+2 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-2, -2));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addEffect(new BoostTargetEffect(2, 2)
                .setTargetPointer(new SecondTargetPointer())
                .concatBy("and"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private GurmagRakshasa(final GurmagRakshasa card) {
        super(card);
    }

    @Override
    public GurmagRakshasa copy() {
        return new GurmagRakshasa(this);
    }
}
