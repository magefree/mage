package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureRandomCreatureEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.ZoneChangeBatchEvent;

import java.util.UUID;

public final class DiabolicFormula extends CardImpl {

    private static final FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard("instant and/or sorcery cards");

    public DiabolicFormula(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}");

        // Whenever one or more instant and/or sorcery cards leave your graveyard, conjure a random creature card with mana value X onto the battlefield, where X is the greatest mana value among those cards.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new DiabolicFormulaEffect(), filter
        ));
    }

    private DiabolicFormula(final DiabolicFormula card) {
        super(card);
    }

    @Override
    public DiabolicFormula copy() {
        return new DiabolicFormula(this);
    }
}

class DiabolicFormulaEffect extends OneShotEffect {

    DiabolicFormulaEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "conjure a random creature card with mana value X onto the battlefield, "
                + "where X is the greatest mana value among those cards";
    }

    private DiabolicFormulaEffect(final DiabolicFormulaEffect effect) {
        super(effect);
    }

    @Override
    public DiabolicFormulaEffect copy() {
        return new DiabolicFormulaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CardsLeaveGraveyardTriggeredAbility trigger = (CardsLeaveGraveyardTriggeredAbility) source;
        ZoneChangeBatchEvent event = (ZoneChangeBatchEvent) trigger.getTriggerEvent();
        int manaValue = trigger.getFilteredEvents(event, game).stream()
                .map(change -> game.getCard(change.getTargetId()))
                .mapToInt(Card::getManaValue).max().orElse(0);
        return ConjureRandomCreatureEffect.conjure(manaValue, Zone.BATTLEFIELD, game, source) != null;
    }
}
