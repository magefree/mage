package mage.cards.g;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.token.IzoniInsectToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class GristVoraciousLarva extends TransformingDoubleFacedCard {

    public GristVoraciousLarva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.INSECT}, "{G}",
                "Grist, the Plague Swarm",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.GRIST}, "BG"
        );

        // Grist, Voracious Larva
        this.getLeftHalfCard().setPT(1, 2);

        // Deathtouch
        this.getLeftHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Whenever Grist, Voracious Larva or another creature you control enters, if it entered from your graveyard or was cast from your graveyard, you may pay {G}. If you do, exile Grist, then return it to the battlefield transformed under its owner's control.
        this.getLeftHalfCard().addAbility(new GristVoraciousLarvaTriggeredAbility());

        // Grist, the Plague Swarm
        this.getRightHalfCard().setStartingLoyalty(3);

        // +1: Create a 1/1 black and green Insect creature token, then mill two cards. Put a deathtouch counter on the token if a black card was milled this way.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new GristThePlagueSwarmPlus1Effect(), 1));

        // -2: Destroy target artifact or enchantment.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -2);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getRightHalfCard().addAbility(ability);

        // -6: For each creature card in your graveyard, create a token that's a copy of it, except it's a 1/1 black and green Insect.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new GristThePlagueSwarmMinus6Effect(), -6));
    }

    private GristVoraciousLarva(final GristVoraciousLarva card) {
        super(card);
    }

    @Override
    public GristVoraciousLarva copy() {
        return new GristVoraciousLarva(this);
    }
}

class GristVoraciousLarvaTriggeredAbility extends EntersBattlefieldThisOrAnotherTriggeredAbility {

    GristVoraciousLarvaTriggeredAbility() {
        super(
                new DoIfCostPaid(
                        new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.IT),
                        new ManaCostsImpl<>("{G}")
                ), StaticFilters.FILTER_PERMANENT_CREATURE, false, true);
        setTriggerPhrase("Whenever {this} or another creature you control enters, "
                + "if it entered from your graveyard or you cast it from your graveyard, ");
    }

    private GristVoraciousLarvaTriggeredAbility(final GristVoraciousLarvaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GristVoraciousLarvaTriggeredAbility copy() {
        return new GristVoraciousLarvaTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        EntersTheBattlefieldEvent zEvent = (EntersTheBattlefieldEvent) event;
        if (zEvent == null) {
            return false;
        }
        Permanent permanent = zEvent.getTarget();
        if (permanent == null) {
            return false;
        }
        Zone fromZone = zEvent.getFromZone();
        boolean fromGraveyard = false;
        if (fromZone == Zone.GRAVEYARD) {
            // Directly from the graveyard
            fromGraveyard = true;
        } else if (fromZone == Zone.STACK) {
            // Get spell in the stack.
            Spell spell = game.getSpellOrLKIStack(permanent.getId());
            if (spell != null && spell.getFromZone() == Zone.GRAVEYARD) {
                // Creature was cast from graveyard
                fromGraveyard = true;
            }
        }
        return fromGraveyard && super.checkTrigger(event, game);
    }
}

class GristThePlagueSwarmPlus1Effect extends OneShotEffect {

    GristThePlagueSwarmPlus1Effect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create a 1/1 black and green Insect creature token, then mill two cards. "
                + "Put a deathtouch counter on the token if a black card was milled this way.";
    }

    private GristThePlagueSwarmPlus1Effect(final GristThePlagueSwarmPlus1Effect effect) {
        super(effect);
    }

    @Override
    public GristThePlagueSwarmPlus1Effect copy() {
        return new GristThePlagueSwarmPlus1Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // Create a 1/1 black and green Insect creature token
        CreateTokenEffect effect = new CreateTokenEffect(new IzoniInsectToken());
        effect.apply(game, source);

        // Then mill two cards
        Cards cards = controller.millCards(2, source, game);

        // Put a deathtouch counter on the token if a black card was milled this way.
        if (cards.getCards(game).stream().anyMatch(card -> card.getColor(game).isBlack())) {
            List<Permanent> tokens = effect
                    .getLastAddedTokenIds()
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (!tokens.isEmpty()) {
                Effect addEffect = new AddCountersTargetEffect(CounterType.DEATHTOUCH.createInstance());
                addEffect.setTargetPointer(new FixedTargets(tokens, game));
                addEffect.apply(game, source);
            }
        }
        return true;
    }
}

class GristThePlagueSwarmMinus6Effect extends OneShotEffect {

    GristThePlagueSwarmMinus6Effect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "For each creature card in your graveyard, create a token that's a copy of it, "
                + "except it's a 1/1 black and green Insect.";
    }

    private GristThePlagueSwarmMinus6Effect(final GristThePlagueSwarmMinus6Effect effect) {
        super(effect);
    }

    @Override
    public GristThePlagueSwarmMinus6Effect copy() {
        return new GristThePlagueSwarmMinus6Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cards = controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game);
        if (cards.isEmpty()) {
            return false;
        }
        for (Card card : cards) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                    null, null, false, 1, false,
                    false, null, 1, 1, false
            );
            effect.setSavedPermanent(new PermanentCard(card, controller.getId(), game));
            effect.setOnlyColor(new ObjectColor("BG"));
            effect.setOnlySubType(SubType.INSECT);
            effect.apply(game, source);
        }
        return true;
    }
}
