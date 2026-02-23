package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class NicolBolasTheRavager extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("creature or planeswalker card from a graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public NicolBolasTheRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDER, SubType.DRAGON}, "{1}{U}{B}{R}",
                "Nicol Bolas, the Arisen",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.BOLAS}, "UBR"
        );

        // Nicol Bolas, the Ravager
        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When Nicol Bolas, the Ravager enters the battlefield, each opponent discards a card.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(StaticValue.get(1), false, TargetController.OPPONENT)));

        // {4}{U}{B}{R}: Exile Nicol Bolas, the Ravager, then return him to the battlefield transformed under his owner's control. Activate this ability only any time you could cast a sorcerry.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.HE),
                new ManaCostsImpl<>("{4}{U}{B}{R}")
        ));

        // Nicol Bolas, the Arisen
        this.getRightHalfCard().setStartingLoyalty(7);

        // +2: Draw two cards.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(2), 2));

        // −3: Nicol Bolas, the Arisen deals 10 damage to target creature or planeswalker.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(10), -3);
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.getRightHalfCard().addAbility(ability);

        // −4: Put target creature or planeswalker card from a graveyard onto the battlefield under your control.
        ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), -4);
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.getRightHalfCard().addAbility(ability);

        // −12: Exile all but the bottom card of target player's library.
        ability = new LoyaltyAbility(new NicolBolasTheArisenEffect(), -12);
        ability.addTarget(new TargetPlayer());
        this.getRightHalfCard().addAbility(ability);
    }

    private NicolBolasTheRavager(final NicolBolasTheRavager card) {
        super(card);
    }

    @Override
    public NicolBolasTheRavager copy() {
        return new NicolBolasTheRavager(this);
    }
}

class NicolBolasTheArisenEffect extends OneShotEffect {

    NicolBolasTheArisenEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile all but the bottom card of target player's library.";
    }

    private NicolBolasTheArisenEffect(final NicolBolasTheArisenEffect effect) {
        super(effect);
    }

    @Override
    public NicolBolasTheArisenEffect copy() {
        return new NicolBolasTheArisenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || controller == null) {
            return false;
        }
        return controller.moveCards(targetPlayer.getLibrary().getTopCards(game, targetPlayer.getLibrary().size() - 1), Zone.EXILED, source, game);
    }
}
