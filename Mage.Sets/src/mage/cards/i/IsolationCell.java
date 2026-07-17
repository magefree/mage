package mage.cards.i;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class IsolationCell extends CardImpl {

    public IsolationCell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever an opponent casts a creature spell, that player loses 2 life unless they pay {2}.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD,
                new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new LoseLifeTargetEffect(2),new ManaCostsImpl<>("{2}")).withTheyText(),
                StaticFilters.FILTER_SPELL_A_CREATURE, false, SetTargetPointer.PLAYER));
    }

    private IsolationCell(final IsolationCell card) {
        super(card);
    }

    @Override
    public IsolationCell copy() {
        return new IsolationCell(this);
    }
}
