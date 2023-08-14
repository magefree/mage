package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.CompleatedAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author miesma
 */
public final class NahiriTheUnforgiving extends CardImpl {

    private static final FilterCard filter = new FilterCard("Equipment or creature with lesser manavalue than Nahiri's loyalty");

    {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
        filter.add(NahiriLoyaltyPredicate.instance);
    }

    public NahiriTheUnforgiving(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{R}{R/W/P}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NAHIRI);

        this.setStartingLoyalty(5);

        //Compleated
        this.addAbility(CompleatedAbility.getInstance());

        // +1: Until your next turn, up to one target creature attacks a player each combat if able.
        LoyaltyAbility ability1 = new LoyaltyAbility(new AttacksIfAbleTargetEffect(Duration.UntilYourNextTurn)
                .setText("Until your next turn, up to one target creature attacks"), 1);
        ability1.addEffect(new NahiriTheUnforgivingRestrictionEffect());
        ability1.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability1);

        // +1: Discard a card, then draw a card.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DiscardControllerEffect(1), 1);
        ability2.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability2);

        // 0: Exile target creature or Equipment card with mana value less than Nahiri's loyalty from your graveyard.
        // Create a token that's a copy of it. That token gains haste. Exile it at the beginning of the next end step.
        LoyaltyAbility ability3 = new LoyaltyAbility(new NahiriTheUnforgivingTokenEffect(), 0);
        ability3.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability3);
    }

    private NahiriTheUnforgiving(final NahiriTheUnforgiving card) {
        super(card);
    }

    @Override
    public NahiriTheUnforgiving copy() {
        return new NahiriTheUnforgiving(this);
    }
}

enum NahiriLoyaltyPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        //mana value less than Nahiri's loyalty
        int manaValue = input.getObject().getManaValue();
        Card card = (Card) input.getSource().getSourceObject(game);
        int loyalty = card.getCounters(game).getCount(CounterType.LOYALTY);
        return manaValue < loyalty;
    }
}

class NahiriTheUnforgivingRestrictionEffect extends RestrictionEffect {

    NahiriTheUnforgivingRestrictionEffect() {
        super(Duration.UntilYourNextTurn);
        this.staticText = " a player each combat if able.";
    }

    NahiriTheUnforgivingRestrictionEffect(final NahiriTheUnforgivingRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public NahiriTheUnforgivingRestrictionEffect copy() {
        return new NahiriTheUnforgivingRestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        // Can't attack a planeswalker
        return game.getPermanent(defenderId) == null;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }
}

class NahiriTheUnforgivingTokenEffect extends OneShotEffect {

    NahiriTheUnforgivingTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target creature or Equipment card with mana value less than Nahiri's loyalty from your graveyard. " +
                "Create a token that's a copy of it. That token gains haste. Exile it at the beginning of the next end step.";
    }

    NahiriTheUnforgivingTokenEffect(final NahiriTheUnforgivingTokenEffect effect) {
        super(effect);
    }

    @Override
    public NahiriTheUnforgivingTokenEffect copy() {
        return new NahiriTheUnforgivingTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        CreateTokenCopyTargetEffect effect
                = new CreateTokenCopyTargetEffect(controller.getId(), null, true);
        effect.setSavedPermanent(new PermanentCard(card, source.getControllerId(), game));
        effect.apply(game, source);
        effect.exileTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}