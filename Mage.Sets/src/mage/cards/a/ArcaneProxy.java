package mage.cards.a;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneProxy extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "instant or sorcery card with mana value less than or equal to this creature's power"
    );

    static {
        filter.add(ArcaneProxyPredicate.instance);
    }

    public ArcaneProxy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Prototype {1}{U}{U} -- 2/1
        this.addAbility(new PrototypeAbility(this, "{1}{U}{U}", 2, 1));

        // When Arcane Proxy enters the battlefield, if you cast it, exile target instant or sorcery card with mana value less than or equal to Arcane Proxy's power from your graveyard. Copy that card. You may cast the copy without paying its mana cost.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ArcaneProxyEffect()),
                CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it, exile target instant or sorcery card with mana value less than or equal to {this}'s " +
                "power from your graveyard. Copy that card. You may cast the copy without paying its mana cost."
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ArcaneProxy(final ArcaneProxy card) {
        super(card);
    }

    @Override
    public ArcaneProxy copy() {
        return new ArcaneProxy(this);
    }
}

enum ArcaneProxyPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(p -> input.getObject().getManaValue() <= p)
                .orElse(false);
    }
}

class ArcaneProxyEffect extends OneShotEffect {

    ArcaneProxyEffect() {
        super(Outcome.Benefit);
    }

    private ArcaneProxyEffect(final ArcaneProxyEffect effect) {
        super(effect);
    }

    @Override
    public ArcaneProxyEffect copy() {
        return new ArcaneProxyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null || !player.chooseUse(
                outcome, "Cast a copy of " + card.getName() + '?', source, game
        )) {
            return false;
        }
        Card copiedCard = game.copyCard(card, source, player.getId());
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(copiedCard, game, false),
                game, false, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }
}
