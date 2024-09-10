package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.ChooseACardNameEffect.TypeOfName;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CheeringFanatic extends CardImpl {

    public CheeringFanatic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Cheering Fanatic attacks, choose a card name. Spells with the chosen name cost {1} less to cast this turn.
        this.addAbility(new AttacksTriggeredAbility(new CheeringFanaticEffect(), false));
    }

    private CheeringFanatic(final CheeringFanatic card) {
        super(card);
    }

    @Override
    public CheeringFanatic copy() {
        return new CheeringFanatic(this);
    }
}

class CheeringFanaticEffect extends OneShotEffect {

    CheeringFanaticEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a card name. Spells with the chosen name cost {1} less to cast this turn";
    }

    private CheeringFanaticEffect(final CheeringFanaticEffect effect) {
        super(effect);
    }

    @Override
    public CheeringFanaticEffect copy() {
        return new CheeringFanaticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new ChooseACardNameEffect(TypeOfName.ALL).apply(game, source);
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (cardName == null) {
            return false;
        }
        FilterCard filter = new FilterCard();
        filter.add(new NamePredicate(cardName));
        ContinuousEffect effect = new SpellsCostReductionAllEffect(filter, 1);
        effect.setDuration(Duration.EndOfTurn);
        game.addEffect(effect, source);
        return true;
    }
}
