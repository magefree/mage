package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.util.PerpetualUtil;

import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SpecializeAbility extends ActivateAsSorceryActivatedAbility {

    private final String cost;

    public SpecializeAbility(String cost, Map<ManaType, String> forms) {
        super(new SpecializeEffect(forms), new ManaCostsImpl<>(cost));
        this.cost = cost;
        FilterCard filter = new FilterCard("a colored card or a card with a basic land type");
        filter.add(SpecializeDiscardPredicate.instance);
        addCost(new DiscardCardCost(filter));
    }

    private SpecializeAbility(final SpecializeAbility ability) {
        super(ability);
        this.cost = ability.cost;
    }

    @Override
    public SpecializeAbility copy() {
        return new SpecializeAbility(this);
    }

    @Override
    public String getRule() {
        return "Specialize " + cost + " <i>(" + cost + ", Discard a card: Perpetually specialize this creature "
                + "into a color of the discarded card or one of its basic land types. Activate only as a sorcery.)</i>";
    }

    static boolean matches(Card card, ManaType color, Game game) {
        switch (color) {
            case WHITE:
                return card.getColor(game).isWhite() || card.hasSubtype(SubType.PLAINS, game);
            case BLUE:
                return card.getColor(game).isBlue() || card.hasSubtype(SubType.ISLAND, game);
            case BLACK:
                return card.getColor(game).isBlack() || card.hasSubtype(SubType.SWAMP, game);
            case RED:
                return card.getColor(game).isRed() || card.hasSubtype(SubType.MOUNTAIN, game);
            case GREEN:
                return card.getColor(game).isGreen() || card.hasSubtype(SubType.FOREST, game);
            default:
                return false;
        }
    }
}

enum SpecializeDiscardPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        for (ManaType color : ManaType.values()) {
            if (SpecializeAbility.matches(input, color, game)) {
                return true;
            }
        }
        return false;
    }
}

class SpecializeEffect extends OneShotEffect {

    private final Map<ManaType, String> forms;

    SpecializeEffect(Map<ManaType, String> forms) {
        super(Outcome.Benefit);
        this.forms = new EnumMap<>(forms);
    }

    private SpecializeEffect(final SpecializeEffect effect) {
        super(effect);
        this.forms = new EnumMap<>(effect.forms);
    }

    @Override
    public SpecializeEffect copy() {
        return new SpecializeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Card original = game.getCard(source.getSourceId());
        DiscardTargetCost discard = source.getCosts().stream().filter(DiscardTargetCost.class::isInstance)
                .map(DiscardTargetCost.class::cast).findFirst().orElse(null);
        if (player == null || permanent == null
                || discard == null || discard.getCards().isEmpty()) {
            return false;
        }
        Card discarded = discard.getCards().get(0);
        Set<String> options = new LinkedHashSet<>();
        forms.forEach((color, name) -> {
            if (SpecializeAbility.matches(discarded, color, game)) {
                options.add(name);
            }
        });
        if (options.isEmpty()) {
            return false;
        }
        String name = options.iterator().next();
        if (options.size() > 1) {
            Choice choice = new ChoiceImpl(true, ChoiceHintType.CARD);
            choice.setMessage("Choose a specialization");
            choice.setChoices(options);
            if (!player.choose(outcome, choice, game)) {
                return false;
            }
            name = choice.getChoice();
        }
        CardInfo info = CardRepository.instance.findCards(new CardCriteria().name(name)).stream().findFirst().orElse(null);
        if (info == null) {
            return false;
        }
        Card form = info.createCard();
        if (original != null) {
            PerpetualUtil.specialize(original, form, game);
        }
        form.setOwnerId(permanent.getOwnerId());
        if (permanent.isToken() || permanent.isCopy()) {
            // Replace an existing copy effect too; tokens have no card blueprint to update.
            form = form.copy();
            form.assignNewId();
            game.copyPermanent(Duration.Custom, new PermanentCard(form, player.getId(), game), permanent.getId(), source, null);
        }
        game.applyEffects();
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.SPECIALIZED, permanent.getId(), source, player.getId()));
        return true;
    }
}
