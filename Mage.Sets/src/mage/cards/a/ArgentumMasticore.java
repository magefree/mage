package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetDiscard;

/**
 * @author TheElk801
 */
public final class ArgentumMasticore extends CardImpl {

    private static final FilterObject<?> filter = new FilterObject<>("multicolored");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public ArgentumMasticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MASTICORE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Protection from multicolored
        this.addAbility(new ProtectionAbility(filter));

        // At the beginning of your upkeep, sacrifice Argentum Masticore unless you discard a card. When you discard a card this way, destroy target nonland permanent an opponent controls with mana value less than or equal to the mana value of the discarded card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ArgentumMasticoreEffect(), TargetController.YOU, false
        ));
    }

    private ArgentumMasticore(final ArgentumMasticore card) {
        super(card);
    }

    @Override
    public ArgentumMasticore copy() {
        return new ArgentumMasticore(this);
    }
}

class ArgentumMasticoreEffect extends OneShotEffect {

    ArgentumMasticoreEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice {this} unless you discard a card. When you discard a card this way, " +
                "destroy target nonland permanent an opponent controls with mana value " +
                "less than or equal to the mana value of the discarded card";
    }

    private ArgentumMasticoreEffect(final ArgentumMasticoreEffect effect) {
        super(effect);
    }

    @Override
    public ArgentumMasticoreEffect copy() {
        return new ArgentumMasticoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player.getHand().isEmpty()
                || (permanent != null && !player.chooseUse(outcome, "Discard a card?", source, game))) {
            permanent.sacrifice(source, game);
            return true;
        }
        TargetDiscard target = new TargetDiscard(player.getId());
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null && permanent != null) {
            permanent.sacrifice(source, game);
            return true;
        }
        FilterPermanent filter = new FilterNonlandPermanent(
                "nonland permanent an opponent controls with mana value " + card.getManaValue() + " or less"
        );
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, card.getManaValue() + 1));
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
