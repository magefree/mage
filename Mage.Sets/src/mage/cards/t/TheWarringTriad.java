package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ManaChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.target.TargetPlayer;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWarringTriad extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount();

    public TheWarringTriad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // As long as there are fewer than eight cards in your graveyard, The Warring Triad isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(xValue, 8)
                .setText("as long as there are fewer than eight cards in your graveyard, {this} isn't a creature")));

        // {T}, Mill a card: Target player adds one mana of any color.
        Ability ability = new SimpleActivatedAbility(new TheWarringTriadEffect(), new TapSourceCost());
        ability.addCost(new MillCardsCost(1));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private TheWarringTriad(final TheWarringTriad card) {
        super(card);
    }

    @Override
    public TheWarringTriad copy() {
        return new TheWarringTriad(this);
    }
}

class TheWarringTriadEffect extends OneShotEffect {

    TheWarringTriadEffect() {
        super(Outcome.Benefit);
        staticText = "target player adds one mana of any color";
    }

    private TheWarringTriadEffect(final TheWarringTriadEffect effect) {
        super(effect);
    }

    @Override
    public TheWarringTriadEffect copy() {
        return new TheWarringTriadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPlayer)
                .ifPresent(player -> player.getManaPool().addMana(
                        ManaChoice.chooseAnyColor(player, game, 1), game, source
                ));
        return true;
    }
}
