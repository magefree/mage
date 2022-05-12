package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DakkonShadowSlayer extends CardImpl {

    public DakkonShadowSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DAKKON);
        this.setStartingLoyalty(0);

        // Dakkon, Shadow Slayer enters the battlefield with a number of loyalty counters on him equal to the number of lands you control.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.LOYALTY.createInstance(0), LandsYouControlCount.instance, true
                ), "with a number of loyalty counters on him equal to the number of lands you control"
        ).addHint(LandsYouControlHint.instance));

        // +1: Surveil 2.
        this.addAbility(new LoyaltyAbility(new SurveilEffect(2), 1));

        // −3: Exile target creature.
        Ability ability = new LoyaltyAbility(new ExileTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // −6: You may put an artifact card from your hand or graveyard onto the battlefield.
        this.addAbility(new LoyaltyAbility(new DakkonShadowSlayerEffect(), -6));
    }

    private DakkonShadowSlayer(final DakkonShadowSlayer card) {
        super(card);
    }

    @Override
    public DakkonShadowSlayer copy() {
        return new DakkonShadowSlayer(this);
    }
}

class DakkonShadowSlayerEffect extends OneShotEffect {

    DakkonShadowSlayerEffect() {
        super(Outcome.Benefit);
        staticText = "you may put an artifact card from your hand or graveyard onto the battlefield";
    }

    private DakkonShadowSlayerEffect(final DakkonShadowSlayerEffect effect) {
        super(effect);
    }

    @Override
    public DakkonShadowSlayerEffect copy() {
        return new DakkonShadowSlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        boolean inGrave = player.getGraveyard().count(StaticFilters.FILTER_CARD_ARTIFACT, game) > 0;
        if (!inGrave && player.getHand().count(StaticFilters.FILTER_CARD_ARTIFACT, game) <1) {
            return false;
        }
        TargetCard target;
        if (!inGrave || player.chooseUse(
                outcome, "Choose a card in your hand or your graveyard?",
                null, "Hand", "Graveyard", source, game
        )) {
            target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_ARTIFACT);
            player.choose(outcome, player.getHand(), target, game);
        } else {
            target = new TargetCardInGraveyard(0, 1, StaticFilters.FILTER_CARD_ARTIFACT);
            player.choose(outcome, player.getGraveyard(), target, game);
        }
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
