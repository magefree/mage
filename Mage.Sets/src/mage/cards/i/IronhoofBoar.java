package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronhoofBoar extends CardImpl {

    public IronhoofBoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Channel — {1}{R}, Discard Ironhoof Boar: Target creature gets +3/+1 and gains trample until end of turn.
        Ability ability = new ChannelAbility("{1}{R}", new BoostTargetEffect(3, 1)
                .setText("target creature gets +3/+1"));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample until end of turn"));
        this.addAbility(ability);
    }

    private IronhoofBoar(final IronhoofBoar card) {
        super(card);
    }

    @Override
    public IronhoofBoar copy() {
        return new IronhoofBoar(this);
    }
}
