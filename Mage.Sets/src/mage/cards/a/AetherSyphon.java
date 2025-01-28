package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.continuous.MaxSpeedGainAbilityEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AetherSyphon extends CardImpl {

    public AetherSyphon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}{U}");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // {2}, {T}: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Max speed -- Whenever you draw a card, each opponent mills two cards.
        this.addAbility(new SimpleStaticAbility(new MaxSpeedGainAbilityEffect(new DrawCardControllerTriggeredAbility(
                new MillCardsEachPlayerEffect(2, TargetController.OPPONENT), false
        ))));
    }

    private AetherSyphon(final AetherSyphon card) {
        super(card);
    }

    @Override
    public AetherSyphon copy() {
        return new AetherSyphon(this);
    }
}
