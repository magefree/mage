package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author Cguy7777
 */
public final class DesdemonaFreedomsEdge extends CardImpl {

    private static final FilterCreatureCard filter
            = new FilterCreatureCard("creature card in your graveyard that's an artifact or that has mana value 3 or less");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                new ManaValuePredicate(ComparisonType.OR_LESS, 3)));
    }

    public DesdemonaFreedomsEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Desdemona, Freedom's Edge attacks,
        // target creature card in your graveyard that's an artifact or that has mana value 3 or less gains escape until end of turn.
        // The escape cost is equal to its mana cost plus exile two other cards from your graveyard.
        Ability ability = new AttacksTriggeredAbility(new DesdemonaFreedomsEdgeEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private DesdemonaFreedomsEdge(final DesdemonaFreedomsEdge card) {
        super(card);
    }

    @Override
    public DesdemonaFreedomsEdge copy() {
        return new DesdemonaFreedomsEdge(this);
    }
}

class DesdemonaFreedomsEdgeEffect extends ContinuousEffectImpl {

    DesdemonaFreedomsEdgeEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "target creature card in your graveyard that's an artifact " +
                "or that has mana value 3 or less gains escape until end of turn. " +
                "The escape cost is equal to its mana cost plus exile two other cards from your graveyard. " +
                "<i>(You may cast it from your graveyard for its escape cost this turn.)</i>";
    }

    private DesdemonaFreedomsEdgeEffect(final DesdemonaFreedomsEdgeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null || card.getManaCost().getText().isEmpty()) {
            return false;
        }
        Ability ability = new EscapeAbility(card, card.getManaCost().getText(), 2);
        ability.setSourceId(card.getId());
        ability.setControllerId(card.getOwnerId());
        game.getState().addOtherAbility(card, ability);
        return true;
    }

    @Override
    public DesdemonaFreedomsEdgeEffect copy() {
        return new DesdemonaFreedomsEdgeEffect(this);
    }
}
