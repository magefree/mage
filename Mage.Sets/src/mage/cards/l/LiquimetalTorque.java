package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LiquimetalTorque extends CardImpl {

    public LiquimetalTorque(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Target nonland permanent becomes an artifact in addition to its other types until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT), new TapSourceCost()
        );
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private LiquimetalTorque(final LiquimetalTorque card) {
        super(card);
    }

    @Override
    public LiquimetalTorque copy() {
        return new LiquimetalTorque(this);
    }
}
