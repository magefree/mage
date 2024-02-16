package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class DisruptingScepter extends CardImpl {

    public DisruptingScepter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {T}: Target player discards a card. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new ManaCostsImpl<>("{3}"), MyTurnCondition.instance);
        ability.addTarget(new TargetPlayer());
        ability.addCost(new TapSourceCost());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private DisruptingScepter(final DisruptingScepter card) {
        super(card);
    }

    @Override
    public DisruptingScepter copy() {
        return new DisruptingScepter(this);
    }
}
