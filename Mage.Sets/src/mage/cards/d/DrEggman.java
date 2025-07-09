package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.FaceVillainousChoiceOpponentsEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrEggman extends CardImpl {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.Discard, new DrEggmanFirstChoice(), new DrEggmanSecondChoice()
    );

    public DrEggman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, draw a card. Then each opponent faces a villainous choice -- That player discards a card, or you may put a Construct, Robot, or Vehicle card from your hand onto the battlefield.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new FaceVillainousChoiceOpponentsEffect(choice).concatBy("Then"));
        this.addAbility(ability);
    }

    private DrEggman(final DrEggman card) {
        super(card);
    }

    @Override
    public DrEggman copy() {
        return new DrEggman(this);
    }
}

class DrEggmanFirstChoice extends VillainousChoice {
    DrEggmanFirstChoice() {
        super("That player discards a card", "Discard a card");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return !player.discard(1, false, false, source, game).isEmpty();
    }
}

class DrEggmanSecondChoice extends VillainousChoice {
    private static final FilterCard filter = new FilterCard("Construct, Robot, or Vehicle card");

    static {
        filter.add(Predicates.or(
                SubType.CONSTRUCT.getPredicate(),
                SubType.ROBOT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    DrEggmanSecondChoice() {
        super("you may put a Construct, Robot, or Vehicle card from your hand onto the battlefield",
                "{controller} may put a Construct, Robot, or Vehicle card from their hand onto the battlefield");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return new PutCardFromHandOntoBattlefieldEffect(filter).apply(game, source);
    }
}
