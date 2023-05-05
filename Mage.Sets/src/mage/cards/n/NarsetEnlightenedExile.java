package mage.cards.n;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NarsetEnlightenedExile extends CardImpl {

    private static final FilterCard filter
            = new FilterNonlandCard("noncreature, nonland card with mana value less than this creature's power");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(NarsetEnlightenedExilePredicate.instance);
    }

    public NarsetEnlightenedExile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Creatures you control have prowess.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ProwessAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("creatures you control have prowess")));

        // Whenever Narset, Enlightened Exile attacks, exile target noncreature, nonland card with mana value less than Narset's power from a graveyard and copy it. You may cast the copy without paying its mana cost.
        Ability ability = new AttacksTriggeredAbility(new NarsetEnlightenedExileEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    private NarsetEnlightenedExile(final NarsetEnlightenedExile card) {
        super(card);
    }

    @Override
    public NarsetEnlightenedExile copy() {
        return new NarsetEnlightenedExile(this);
    }
}

enum NarsetEnlightenedExilePredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(p -> input.getObject().getManaValue() < p)
                .orElse(false);
    }
}

class NarsetEnlightenedExileEffect extends OneShotEffect {

    NarsetEnlightenedExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile target noncreature, nonland card with mana value less than {this}'s power " +
                "from a graveyard and copy it. You may cast the copy without paying its mana cost";
    }

    private NarsetEnlightenedExileEffect(final NarsetEnlightenedExileEffect effect) {
        super(effect);
    }

    @Override
    public NarsetEnlightenedExileEffect copy() {
        return new NarsetEnlightenedExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Card copiedCard = game.copyCard(card, source, source.getControllerId());
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(copiedCard, game, true),
                game, true, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
