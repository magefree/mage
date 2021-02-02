
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ReservoirWalker extends CardImpl {

    public ReservoirWalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Reservoir Walker enters the battlefield, you gain 3 life and get {E}{E}{E}.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3));
        Effect effect = new GetEnergyCountersControllerEffect(3);
        effect.setText("and get {E}{E}{E}");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private ReservoirWalker(final ReservoirWalker card) {
        super(card);
    }

    @Override
    public ReservoirWalker copy() {
        return new ReservoirWalker(this);
    }
}
