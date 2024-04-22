package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetStackAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterStackObject;
import mage.target.common.TargetTriggeredAbility;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class StrionicResonator extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("triggered ability you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public StrionicResonator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetStackAbilityEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetTriggeredAbility(filter));
        this.addAbility(ability);
    }

    private StrionicResonator(final StrionicResonator card) {
        super(card);
    }

    @Override
    public StrionicResonator copy() {
        return new StrionicResonator(this);
    }
}
