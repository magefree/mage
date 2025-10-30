package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyLeeArtfulAcrobat extends CardImpl {

    public TyLeeArtfulAcrobat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PERFORMER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever Ty Lee attacks, you may pay {1}. When you do, target creature can't block this turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(ability, new GenericManaCost(1), "Pay {1}?")));
    }

    private TyLeeArtfulAcrobat(final TyLeeArtfulAcrobat card) {
        super(card);
    }

    @Override
    public TyLeeArtfulAcrobat copy() {
        return new TyLeeArtfulAcrobat(this);
    }
}
