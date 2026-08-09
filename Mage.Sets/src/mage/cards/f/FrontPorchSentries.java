package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FrontPorchSentries extends CardImpl {

    public FrontPorchSentries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature dies, target creature an opponent controls gets -1/-1 until end of turn.
        Ability ability = new DiesSourceTriggeredAbility(new BoostTargetEffect(-1, -1));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private FrontPorchSentries(final FrontPorchSentries card) {
        super(card);
    }

    @Override
    public FrontPorchSentries copy() {
        return new FrontPorchSentries(this);
    }
}
