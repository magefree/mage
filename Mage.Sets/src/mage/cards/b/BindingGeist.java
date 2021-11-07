package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BindingGeist extends CardImpl {

    public BindingGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.s.SpectralBinding.class;

        // Whenever Binding Geist attacks, target creature an opponent controls gets -2/-0 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(-2, 0));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Disturb {1}{U}
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{1}{U}")));
    }

    private BindingGeist(final BindingGeist card) {
        super(card);
    }

    @Override
    public BindingGeist copy() {
        return new BindingGeist(this);
    }
}
