package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author nantuko
 */
public final class MimicVat extends CardImpl {

    public MimicVat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Imprint - Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with Mimic Vat to its owner's graveyard.
        this.addAbility(new DiesCreatureTriggeredAbility(Zone.BATTLEFIELD, new MimicVatEffect(),
                true, StaticFilters.FILTER_CREATURE_NON_TOKEN, SetTargetPointer.PERMANENT
        ).setAbilityWord(AbilityWord.IMPRINT));

        // {3}, {T}: Create a token that's a copy of the exiled card. It gains haste. Exile it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new MimicVatCreateTokenEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MimicVat(final MimicVat card) {
        super(card);
    }

    @Override
    public MimicVat copy() {
        return new MimicVat(this);
    }
}

class MimicVatEffect extends OneShotEffect {

    MimicVatEffect() {
        super(Outcome.Benefit);
        staticText = "exile that card. If you do, return each other card exiled with {this} to its owner's graveyard";
    }

    private MimicVatEffect(final MimicVatEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || permanent == null) {
            return false;
        }
        // Imprint a new one
        Set<Card> newCards = new CardsImpl(getTargetPointer().getTargets(game, source)).getCards(game);
        if (!newCards.isEmpty()) {
            game.informPlayers("found cards to imprint: " + newCards.size());
            // return older cards to graveyard
            Set<Card> toGraveyard = permanent.getImprinted().stream()
                    .map(game::getCard)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            controller.moveCards(toGraveyard, Zone.GRAVEYARD, source, game);
            permanent.clearImprinted(game);

            controller.moveCardsToExile(newCards, source, game, true, source.getSourceId(), permanent.getName() + " (Imprint)");
            newCards.forEach(c -> permanent.imprint(c.getId(), game));
        }
        return true;
    }

    @Override
    public MimicVatEffect copy() {
        return new MimicVatEffect(this);
    }

}

class MimicVatCreateTokenEffect extends OneShotEffect {

    MimicVatCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a token that's a copy of a card exiled with {this}. It gains haste. Exile it at the beginning of the next end step";
    }

    private MimicVatCreateTokenEffect(final MimicVatCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public MimicVatCreateTokenEffect copy() {
        return new MimicVatCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null || permanent.getImprinted().isEmpty()) {
            return false;
        }
        Card card = game.getCard(permanent.getImprinted().get(0));
        if (permanent.getImprinted().size() > 1) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                TargetCard target = new TargetCard(Zone.ALL, new FilterCard());
                target.setRequired(true);
                Cards cards = new CardsImpl();
                for (UUID cardId : permanent.getImprinted()) {
                    Card c = game.getCard(cardId);
                    if (c != null) {
                        cards.add(c);
                    }
                }
                player.chooseTarget(Outcome.Neutral, cards, target, source, game);
                card = game.getCard(target.getFirstTarget());
            }
        } else {
            game.informPlayers("only one imprinted card");
        }
        if (card != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
            effect.setTargetPointer(new FixedTarget(card, game));
            effect.apply(game, source);
            for (Permanent addedToken : effect.getAddedPermanents()) {
                ExileTargetEffect exileEffect = new ExileTargetEffect();
                exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }

}
