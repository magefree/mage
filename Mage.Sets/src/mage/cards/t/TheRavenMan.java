package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.PlayerDiscardedThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.BlackBirdToken;
import mage.watchers.common.DiscardedCardWatcher;

/**
 *
 * @author weirddan455
 */
public final class TheRavenMan extends CardImpl {

    public TheRavenMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of each end step, if a player discarded a card this turn, create a 1/1 black Bird creature token with flying and "This creature can't block."
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new BlackBirdToken()),
                TargetController.ANY,
                PlayerDiscardedThisTurnCondition.instance,
                false
        ), new DiscardedCardWatcher());

        // {3}{B}, {T}: Each opponent discards a card. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT), new ManaCostsImpl<>("{3}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheRavenMan(final TheRavenMan card) {
        super(card);
    }

    @Override
    public TheRavenMan copy() {
        return new TheRavenMan(this);
    }
}
