package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.StaticHint;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.BatToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

public class TimotharBaronOfBats extends CardImpl {

    private static final FilterPermanent anotherVampireFilter = new FilterControlledCreaturePermanent("another nontoken Vampire you control");
    static {
        anotherVampireFilter.add(AnotherPredicate.instance);
        anotherVampireFilter.add(SubType.VAMPIRE.getPredicate());
    }

    public TimotharBaronOfBats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.VAMPIRE);
        this.addSubType(SubType.NOBLE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ward—Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost()));

        // Whenever another nontoken Vampire you control dies, you may pay {1} and exile it.
        // If you do, create a 1/1 black Bat creature token with flying.
        // It gains “When this creature deals combat damage to a player,
        // sacrifice it and return the exiled card to the battlefield tapped.”
        this.addAbility(new DiesCreatureTriggeredAbility(
                new TimotharBaronOfBatsCreateBatEffect(),
                false,
                anotherVampireFilter,
                true
        ));
    }

    private TimotharBaronOfBats(final TimotharBaronOfBats card) { super(card); }

    @Override
    public Card copy() { return new TimotharBaronOfBats(this); }
}

class TimotharBaronOfBatsCreateBatEffect extends OneShotEffect {

    TimotharBaronOfBatsCreateBatEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {1} and exile it. " +
                "If you do, create a 1/1 black Bat creature token with flying. " +
                "It gains \"When this creature deals combat damage to a player, " +
                "sacrifice it and return the exiled card to the battlefield tapped\".";
    }

    private TimotharBaronOfBatsCreateBatEffect(final TimotharBaronOfBatsCreateBatEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        // Check vampire card still exists and is still in the graveyard
        Card vampireCard = game.getCard(targetPointer.getFirst(game, source));
        if (vampireCard == null) { return false; }

        // Create costs
        ManaCosts<ManaCost> costs = new ManaCostsImpl<>("{1}");
        // Exiling the card is handled after the owner pays the mana cost.
        String costPromptMessage = "Pay {1} and exile " + vampireCard.getName() + "? " +
                "If you do, create a create a 1/1 black Bat creature token with flying. " +
                "It gains \"When this creature deals combat damage to a player, " +
                "sacrifice it and return the exiled card to the battlefield tapped\".";

        // Ask player if they wanna pay cost
        if (!costs.canPay(source, source, controller.getId(), game)) { return false; }
        if (!controller.chooseUse(Outcome.Benefit, costPromptMessage, source, game)) { return false; }
        if (!costs.pay(source, game, source, controller.getId(),false)) { return false; }
        // Exile the card as part of the cost.
        // Handled this way so that the player doesn't have to dig through their graveyard for the card.
        controller.moveCards(vampireCard, Zone.EXILED, source, game);

        BatToken bat = new BatToken();
        bat.putOntoBattlefield(1, game, source);

        DealsCombatDamageToAPlayerTriggeredAbility sacAndReturnAbility = new DealsCombatDamageToAPlayerTriggeredAbility(
                new SacrificeSourceEffect(),
                false,
                "When this creature deals combat damage to a player, " +
                        "sacrifice it and return the exiled card to the battlefield tapped",
                false);
        sacAndReturnAbility.addEffect(new TimotharBaronOfBatsReturnEffect(new MageObjectReference(vampireCard, game)));
        sacAndReturnAbility.addHint(new StaticHint("Exiled card: " + vampireCard.getName()));

        GainAbilityTargetEffect gainAbilityTargetEffect = new GainAbilityTargetEffect(sacAndReturnAbility, Duration.Custom);
        gainAbilityTargetEffect.setTargetPointer(new FixedTargets(bat, game));
        game.addEffect(gainAbilityTargetEffect, source);

        return true;
    }

    @Override
    public TimotharBaronOfBatsCreateBatEffect copy() { return new TimotharBaronOfBatsCreateBatEffect(this); }
}

class TimotharBaronOfBatsReturnEffect extends OneShotEffect {

    private final MageObjectReference morOfCardToReturn;

    TimotharBaronOfBatsReturnEffect(MageObjectReference morOfCardToReturn) {
        super(Outcome.PutCreatureInPlay);
        staticText = "return the exiled card to the battlefield tapped";
        this.morOfCardToReturn = morOfCardToReturn;
    }

    private TimotharBaronOfBatsReturnEffect(final TimotharBaronOfBatsReturnEffect effect) {
        super(effect);
        this.morOfCardToReturn = effect.morOfCardToReturn;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) { return false; }

        Card card = morOfCardToReturn.getCard(game);
        if (card == null) { return false; }

        return player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
    }

    @Override
    public Effect copy() { return new TimotharBaronOfBatsReturnEffect(this); }
}
