package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class NicolBolasTheArisen extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or planeswalker card from a graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public NicolBolasTheArisen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOLAS);

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.nightCard = true;

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(7));

        // +2: Draw two cards.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(2), 2));

        // −3: Nicol Bolas, the Arisen deals 10 damage to target creature or planeswalker.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(10), -3);
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);

        // −4: Put target creature or planeswalker card from a graveyard onto the battlefield under your control.
        ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), -4);
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

        // −12: Exile all but the bottom card of target player's library.
        ability = new LoyaltyAbility(new NicolBolasTheArisenEffect(), -12);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private NicolBolasTheArisen(final NicolBolasTheArisen card) {
        super(card);
    }

    @Override
    public NicolBolasTheArisen copy() {
        return new NicolBolasTheArisen(this);
    }
}

class NicolBolasTheArisenEffect extends OneShotEffect {

    public NicolBolasTheArisenEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile all but the bottom card of target player's library.";
    }

    public NicolBolasTheArisenEffect(final NicolBolasTheArisenEffect effect) {
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
