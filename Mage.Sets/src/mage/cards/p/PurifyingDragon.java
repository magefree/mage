package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PurifyingDragon extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public PurifyingDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Purifying Dragon attacks, it deals 1 damage to target creature defending player controls. If that creature is a Zombie, Purifying Dragon deals 2 damage to that creature instead.
        Ability ability = new AttacksTriggeredAbility(new PurifyingDragonEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private PurifyingDragon(final PurifyingDragon card) {
        super(card);
    }

    @Override
    public PurifyingDragon copy() {
        return new PurifyingDragon(this);
    }
}

class PurifyingDragonEffect extends OneShotEffect {

    PurifyingDragonEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 1 damage to target creature defending player controls. " +
                "If that creature is a Zombie, {this} deals 2 damage to it instead";
    }

    private PurifyingDragonEffect(final PurifyingDragonEffect effect) {
        super(effect);
    }

    @Override
    public PurifyingDragonEffect copy() {
        return new PurifyingDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return permanent.damage(
                permanent.hasSubtype(SubType.ZOMBIE, game) ? 2 : 1,
                source.getSourceId(), source, game
        ) > 0;
    }
}
