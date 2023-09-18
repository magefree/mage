package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateRoleAttachedSourceEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaunsbaneTroll extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.AURA, "an Aura attached to {this}");

    static {
        filter.add(FaunsbaneTrollPredicate.instance);
    }

    public FaunsbaneTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Faunsbane Troll enters the battlefield, create a Monster Role token attached to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateRoleAttachedSourceEffect(RoleType.MONSTER)));

        // {1}, Sacrifice an Aura attached to Faunsbane Troll: Faunsbane Troll fights target creature you don't control. If that creature would die this turn, exile it instead. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new FightTargetSourceEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new ExileTargetIfDiesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private FaunsbaneTroll(final FaunsbaneTroll card) {
        super(card);
    }

    @Override
    public FaunsbaneTroll copy() {
        return new FaunsbaneTroll(this);
    }
}

enum FaunsbaneTrollPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getSource().getSourceId().equals(input.getObject().getAttachedTo());
    }
}