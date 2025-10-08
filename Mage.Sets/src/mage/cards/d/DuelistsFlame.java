package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DuelistsFlame extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("blocked creature you control");

    static {
        filter.add(BlockedPredicate.instance);
    }

    public DuelistsFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");

        // Until end of turn, target blocked creature you control gets +X/+0 and gains trample and "Whenever this creature deals combat damage to a player, look at that many cards from the top of your library. Exile up to one nonland card from among them and put the rest on the bottom of your library in a random order. You may cast the exiled card without paying its mana cost."
        this.getSpellAbility().addEffect(new BoostTargetEffect(GetXValue.instance, StaticValue.get(0))
                .setText("until end of turn, target blocked creature you control gets +X/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("and has trample"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(new DuelistsFlameEffect())
        ).setText("and \"Whenever this creature deals combat damage to a player, " +
                "look at that many cards from the top of your library. Exile up to one nonland card " +
                "from among them and put the rest on the bottom of your library in a random order. " +
                "You may cast the exiled card without paying its mana cost.\""));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private DuelistsFlame(final DuelistsFlame card) {
        super(card);
    }

    @Override
    public DuelistsFlame copy() {
        return new DuelistsFlame(this);
    }
}

class DuelistsFlameEffect extends OneShotEffect {

    DuelistsFlameEffect() {
        super(Outcome.Benefit);
        staticText = "look at that many cards from the top of your library. " +
                "Exile up to one nonland card from among them and put the rest on the bottom of your library " +
                "in a random order. You may cast the exiled card without paying its mana cost";
    }

    private DuelistsFlameEffect(final DuelistsFlameEffect effect) {
        super(effect);
    }

    @Override
    public DuelistsFlameEffect copy() {
        return new DuelistsFlameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int damage = (Integer) getValue("damage");
        if (player == null || damage < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, damage));
        TargetCard target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_NON_LAND);
        player.choose(Outcome.PlayForFree, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        player.moveCards(card, Zone.EXILED, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        if (card != null) {
            CardUtil.castSpellWithAttributesForFree(player, source, game, card);
        }
        return true;
    }
}
