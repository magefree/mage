package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class TheThirdDoctor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("noncreature token you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(TokenPredicate.TRUE);
    }

    public TheThirdDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD, SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // The Third Doctor gets +1/+1 for each noncreature token you control.
        PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(count, count, Duration.WhileOnBattlefield)));

        // When The Third Doctor enters the battlefield, create your choice of a Clue token, a Food token, or a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TheThirdDoctorEffect()));
    }

    private TheThirdDoctor(final TheThirdDoctor card) {
        super(card);
    }

    @Override
    public TheThirdDoctor copy() {
        return new TheThirdDoctor(this);
    }
}

class TheThirdDoctorEffect extends OneShotEffect {

    private static final Set<String> choices = new HashSet<>();

    static {
        choices.add("Clue");
        choices.add("Food");
        choices.add("Treasure");
    }

    public TheThirdDoctorEffect() {
        super(Outcome.Benefit);
        staticText = "create your choice of a Clue token, a Food token, or a Treasure token";
    }

    private TheThirdDoctorEffect(final TheThirdDoctorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Choice choiceImpl = new ChoiceImpl(true);
        choiceImpl.setMessage("Choose type of token to create");
        choiceImpl.setChoices(choices);
        if (controller.choose(outcome, choiceImpl, game)) {
            Token token;
            switch (choiceImpl.getChoice()) {
                case "Clue": {
                    token = new ClueArtifactToken();
                    break;
                }
                case "Food": {
                    token = new FoodToken();
                    break;
                }
                case "Treasure":
                default: {
                    token = new TreasureToken();
                    break;
                }
            }
            return token.putOntoBattlefield(1, game, source, source.getControllerId());
        }

        return false;
    }

    @Override
    public TheThirdDoctorEffect copy() {
        return new TheThirdDoctorEffect(this);
    }
}
