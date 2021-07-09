package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CatacombDragon extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("a nonartifact, non-Dragon creature");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(Predicates.not(SubType.DRAGON.getPredicate()));
    }

    public CatacombDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Catacomb Dragon becomes blocked by a nonartifact, non-Dragon creature, that creature gets -X/-0 until end of turn, where X is half the creature's power, rounded down.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new CatacombDragonEffect(), filter, false));
    }

    private CatacombDragon(final CatacombDragon card) {
        super(card);
    }

    @Override
    public CatacombDragon copy() {
        return new CatacombDragon(this);
    }
}

class CatacombDragonEffect extends OneShotEffect {

    CatacombDragonEffect() {
        super(Outcome.Benefit);
        staticText = "that creature gets -X/-0 until end of turn, where X is half the creature's power, rounded down.";
    }

    private CatacombDragonEffect(final CatacombDragonEffect effect) {
        super(effect);
    }

    @Override
    public CatacombDragonEffect copy() {
        return new CatacombDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        int unBoost = -1 * Math.floorDiv(permanent.getPower().getValue(), 2);
        game.addEffect(new BoostTargetEffect(unBoost, 0), source);
        return true;
    }
}