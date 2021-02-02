
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class RogueRefiner extends CardImpl {

    public RogueRefiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Rogue Refiner enters the battlefield, draw a card and you get {E}{E}.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false);
        Effect effect = new GetEnergyCountersControllerEffect(2);
        effect.setText("and you get {E}{E}");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private RogueRefiner(final RogueRefiner card) {
        super(card);
    }

    @Override
    public RogueRefiner copy() {
        return new RogueRefiner(this);
    }
}
