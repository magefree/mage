
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesColorOrColorsTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class Shyft extends CardImpl {

    public Shyft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, you may have Shyft become the color or colors of your choice.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ShyftEffect(), TargetController.YOU, true));
    }

    private Shyft(final Shyft card) {
        super(card);
    }

    @Override
    public Shyft copy() {
        return new Shyft(this);
    }
}

class ShyftEffect extends OneShotEffect {

    ShyftEffect() {
        super(Outcome.Benefit);
        this.staticText = "have {this} become the color or colors of your choice.";
    }

    private ShyftEffect(final ShyftEffect effect) {
        super(effect);
    }

    @Override
    public ShyftEffect copy() {
        return new ShyftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Effect effect = new BecomesColorOrColorsTargetEffect(Duration.Custom);
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
        return effect.apply(game, source);
    }
}
