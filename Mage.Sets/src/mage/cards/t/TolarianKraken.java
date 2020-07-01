package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TolarianKraken extends CardImpl {

    public TolarianKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Whenever you draw a card, you may pay {1}. When you do, you may tap or untap target creature.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new MayTapOrUntapTargetEffect(), false,
                "you may tap or untap target creature"
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new DrawCardControllerTriggeredAbility(new DoWhenCostPaid(
                ability, new GenericManaCost(1),
                "Pay {1} to tap or untap a creature?"
        ), false));
    }

    private TolarianKraken(final TolarianKraken card) {
        super(card);
    }

    @Override
    public TolarianKraken copy() {
        return new TolarianKraken(this);
    }
}
