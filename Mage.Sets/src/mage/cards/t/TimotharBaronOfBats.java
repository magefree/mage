package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.token.BatToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

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
                new DoIfCostPaid(new TimotharBaronOfBatsCreateBatEffect(), new ManaCostsImpl<>("{1}"))
                        .setText("you may pay {1} and exile it. " +
                                "If you do, create a 1/1 black Bat creature token with flying. " +
                                "It gains \"When this creature deals combat damage to a player, " +
                                "sacrifice it and return the exiled card to the battlefield tapped\"."),
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
        staticText = "exile it. " +
                "If you do, create a 1/1 black Bat creature token with flying. " +
                "It gains \"When this creature deals combat damage to a player, " +
                "sacrifice it and return the exiled card to the battlefield tapped\".";
    }

    private TimotharBaronOfBatsCreateBatEffect(final TimotharBaronOfBatsCreateBatEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) { return false; }

        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card == null) { return false; }

        BatToken bat = new BatToken();

        // Create ability to sack the bat and return the vampire
        TriggeredAbility ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new TimotharBaronOfBatsReturnEffect(card, game),
                false);
        ability.addEffect(new SacrificeSourceEffect());

        // Turn into a gain ability effect and point it at the bat
        ContinuousEffect effect = new GainAbilityTargetEffect(ability, Duration.Custom);
        effect.setTargetPointer(new FixedTarget(bat.getId(), game));

        // Add the effect and the bat to the
        game.addEffect(effect, source);
        new CreateTokenEffect(bat, 1).apply(game, source);

        return true;
    }

    @Override
    public TimotharBaronOfBatsCreateBatEffect copy() { return new TimotharBaronOfBatsCreateBatEffect(this); }
}

class TimotharBaronOfBatsReturnEffect extends OneShotEffect {

    private final MageObjectReference morOfCardToReturn;

    TimotharBaronOfBatsReturnEffect(Card card, Game game) {
        super(Outcome.PutCreatureInPlay);
        staticText = "return the exiled card to the battlefield tapped";
        this.morOfCardToReturn = new MageObjectReference(card, game);
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
