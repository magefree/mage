package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.BlueAngelToken;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class LyraTolarianArchangel extends CardImpl {

    public LyraTolarianArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, if you've drawn three or more cards this turn, create a 3/3 blue Angel creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.ANY, new CreateTokenEffect(new BlueAngelToken()),
            false, LyraTolarianArchangelCondition.instance
        ), new CardsAmountDrawnThisTurnWatcher());

        // {3}{U}{U}: Until end of turn, whenever Lyra deals combat damage to a player, draw two cards.
        Ability gainedAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(2));
        this.addAbility(new SimpleActivatedAbility(
            new GainAbilitySourceEffect(gainedAbility, Duration.EndOfTurn)
                .setText("until end of turn, whenever {this} deals combat damage to a player, draw two cards"),
            new ManaCostsImpl<>("{3}{U}{U}")
        ));
    }

    private LyraTolarianArchangel(final LyraTolarianArchangel card) {
        super(card);
    }

    @Override
    public LyraTolarianArchangel copy() {
        return new LyraTolarianArchangel(this);
    }
}

enum LyraTolarianArchangelCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        return watcher != null && watcher.getAmountCardsDrawn(source.getControllerId()) >= 3;
    }

    @Override
    public String toString() {
        return "you drew three or more cards this turn";
    }
}
