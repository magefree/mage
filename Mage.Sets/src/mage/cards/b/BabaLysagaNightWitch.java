package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author TheElk801
 */
public final class BabaLysagaNightWitch extends CardImpl {

    public BabaLysagaNightWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice up to three permanents: If there were three or more card types among the sacrificed permanents, each opponent loses 3 life, you gain 3 life, and you draw three cards.
        Ability ability = new SimpleActivatedAbility(new BabaLysagaNightWitchEffect(), new TapSourceCost());
        ability.addCost(new BabaLysagaNightWitchSacrificeCost());
        this.addAbility(ability);
    }

    private BabaLysagaNightWitch(final BabaLysagaNightWitch card) {
        super(card);
    }

    @Override
    public BabaLysagaNightWitch copy() {
        return new BabaLysagaNightWitch(this);
    }
}

class BabaLysagaNightWitchSacrificeCost extends SacrificeTargetCost {

    private Set<CardType> sacrificeTypes = new HashSet<>();

    BabaLysagaNightWitchSacrificeCost() {
        super(new TargetControlledPermanent(0, 3, StaticFilters.FILTER_CONTROLLED_PERMANENTS, true));
        setText("Sacrifice up to three permanents");
    }

    private BabaLysagaNightWitchSacrificeCost(final BabaLysagaNightWitchSacrificeCost cost) {
        super(cost);
        this.sacrificeTypes.addAll(cost.sacrificeTypes);
    }

    @Override
    public BabaLysagaNightWitchSacrificeCost copy() {
        return new BabaLysagaNightWitchSacrificeCost(this);
    }

    Set<CardType> getCardTypes() {
        return this.sacrificeTypes;
    }

    @Override
    protected void addSacrificeTarget(Game game, Permanent permanent) {
        super.addSacrificeTarget(game, permanent);
        // The Permanent's types will lose the gained/lose types after the sacrifice, so they are stored right before.
        this.sacrificeTypes.addAll(permanent.getCardType(game));
    }
}

class BabaLysagaNightWitchEffect extends OneShotEffect {

    BabaLysagaNightWitchEffect() {
        super(Outcome.Benefit);
        staticText = "if there were three or more card types among the sacrificed permanents, " +
                "each opponent loses 3 life, you gain 3 life, and you draw three cards";
    }

    private BabaLysagaNightWitchEffect(final BabaLysagaNightWitchEffect effect) {
        super(effect);
    }

    @Override
    public BabaLysagaNightWitchEffect copy() {
        return new BabaLysagaNightWitchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (CardUtil
                .castStream(source.getCosts().stream(), BabaLysagaNightWitchSacrificeCost.class)
                .filter(Objects::nonNull)
                .map(BabaLysagaNightWitchSacrificeCost::getCardTypes)
                .flatMap(Collection::stream)
                .distinct()
                .count() < 3) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.loseLife(3, game, source, false);
            }
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(3, game, source);
            controller.drawCards(3, source, game);
        }
        return true;
    }
}
