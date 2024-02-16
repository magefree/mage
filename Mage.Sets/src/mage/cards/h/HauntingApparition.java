package mage.cards.h;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class HauntingApparition extends CardImpl {

    public HauntingApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Haunting Apparition enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));

        // Haunting Apparition's power is equal to 1 plus the number of green creature cards in the chosen player's graveyard.
        Effect effect = new SetBasePowerSourceEffect(OnePlusGreenCreatureCardsInChosenOpponentsGraveyardCount.instance);
        effect.setText("{this}'s power is equal to 1 plus the number of green creature cards in the chosen player's graveyard");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));
    }

    private HauntingApparition(final HauntingApparition card) {
        super(card);
    }

    @Override
    public HauntingApparition copy() {
        return new HauntingApparition(this);
    }
}

enum OnePlusGreenCreatureCardsInChosenOpponentsGraveyardCount implements DynamicValue {
    instance;
    private static final FilterCreatureCard filter = new FilterCreatureCard();

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            UUID playerId = (UUID) game.getState().getValue(sourceAbility.getSourceId() + ChooseOpponentEffect.VALUE_KEY);
            Player chosenOpponent = game.getPlayer(playerId);
            if (chosenOpponent != null) {
                return 1 + chosenOpponent.getGraveyard().count(filter, game);
            }
        }
        return 1;
    }

    @Override
    public OnePlusGreenCreatureCardsInChosenOpponentsGraveyardCount copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "1 plus the number of green creature cards in the chosen player's graveyard";
    }
}
