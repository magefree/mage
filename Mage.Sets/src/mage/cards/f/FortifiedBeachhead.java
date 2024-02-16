package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author weirddan455
 */
public final class FortifiedBeachhead extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SOLDIER, "Soldiers");

    public FortifiedBeachhead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As Fortified Beachhead enters the battlefield, you may reveal a Soldier card from your hand. Fortified Beachhead enters the battlefield tapped unless you revealed a Soldier card this way or you control a Soldier.
        this.addAbility(new AsEntersBattlefieldAbility(new FortifiedBeachheadEffect()));

        // {T}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // {5}, {T}: Soldiers you control get +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter),
                new GenericManaCost(5)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private FortifiedBeachhead(final FortifiedBeachhead card) {
        super(card);
    }

    @Override
    public FortifiedBeachhead copy() {
        return new FortifiedBeachhead(this);
    }
}

class FortifiedBeachheadEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Soldier card from your hand");

    static {
        filter.add(SubType.SOLDIER.getPredicate());
    }

    public FortifiedBeachheadEffect() {
        super(Outcome.Tap);
        this.staticText = "you may reveal a Soldier card from your hand. {this} enters the battlefield tapped unless you revealed a Soldier card this way or you control a Soldier.";
    }

    private FortifiedBeachheadEffect(final FortifiedBeachheadEffect effect) {
        super(effect);
    }

    @Override
    public FortifiedBeachheadEffect copy() {
        return new FortifiedBeachheadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanentEntering(source.getSourceId());
        if (land == null) {
            return false;
        }
        if (revealSoldier(game, source)) {
            return true;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.hasSubtype(SubType.SOLDIER, game)) {
                return true;
            }
        }
        land.setTapped(true);
        return true;
    }

    private boolean revealSoldier(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(0, 1, filter);
        target.withChooseHint("to reveal");
        controller.chooseTarget(Outcome.Benefit, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        return true;
    }
}
