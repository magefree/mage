package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttachedToSourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThreeDogGalaxyNewsDJ extends CardImpl {

    public ThreeDogGalaxyNewsDJ(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Whenever you attack, you may pay {2} and sacrifice an Aura attached to Three Dog, Galaxy News DJ. When you sacrifice an Aura this way, for each other attacking creature you control, create a token that's a copy of that Aura attached to that creature.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new ThreeDogGalaxyNewsDJEffect(), 1));
    }

    private ThreeDogGalaxyNewsDJ(final ThreeDogGalaxyNewsDJ card) {
        super(card);
    }

    @Override
    public ThreeDogGalaxyNewsDJ copy() {
        return new ThreeDogGalaxyNewsDJ(this);
    }
}

class ThreeDogGalaxyNewsDJEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.AURA, "Aura attached to this creature");

    static {
        filter.add(AttachedToSourcePredicate.instance);
    }

    ThreeDogGalaxyNewsDJEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {2} and sacrifice an Aura attached to {this}. " +
                "When you sacrifice an Aura this way, for each other attacking creature you control, " +
                "create a token that's a copy of that Aura attached to that creature";
    }

    private ThreeDogGalaxyNewsDJEffect(final ThreeDogGalaxyNewsDJEffect effect) {
        super(effect);
    }

    @Override
    public ThreeDogGalaxyNewsDJEffect copy() {
        return new ThreeDogGalaxyNewsDJEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        SacrificeTargetCost sacCost = new SacrificeTargetCost(filter);
        Cost cost = new CompositeCost(
                new GenericManaCost(2), sacCost,
                "Pay {2} and sacrifice an Aura attached to this creature"
        );
        if (!cost.canPay(source, source, source.getControllerId(), game)
                || !player.chooseUse(outcome, "Pay {2} and sacrifice an Aura?", source, game)
                || !cost.pay(source, game, source, source.getControllerId(), false)) {
            return false;
        }
        Permanent permanent = sacCost
                .getPermanents()
                .stream()
                .findFirst()
                .orElse(null);
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new ThreeDogGalaxyNewsDJTokenEffect(permanent), false
        ), source);
        return true;
    }
}

class ThreeDogGalaxyNewsDJTokenEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterAttackingCreature();

    static {
        filter.add(AnotherPredicate.instance);
    }

    private final Permanent sacPermanent;

    ThreeDogGalaxyNewsDJTokenEffect(Permanent permanent) {
        super(Outcome.Benefit);
        this.sacPermanent = permanent != null ? permanent.copy() : null;
        staticText = "for each other attacking creature you control, " +
                "create a token that's a copy of that Aura attached to that creature";
    }

    private ThreeDogGalaxyNewsDJTokenEffect(final ThreeDogGalaxyNewsDJTokenEffect effect) {
        super(effect);
        this.sacPermanent = effect.sacPermanent != null ? effect.sacPermanent.copy() : null;
    }

    @Override
    public ThreeDogGalaxyNewsDJTokenEffect copy() {
        return new ThreeDogGalaxyNewsDJTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (sacPermanent == null) {
            return false;
        }
        for (Permanent attacker : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            new CreateTokenCopyTargetEffect()
                    .setSavedPermanent(sacPermanent)
                    .setAttachedTo(attacker.getId())
                    .apply(game, source);
        }
        return true;
    }
}
