package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FriendsForeverAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChiefJimHopper extends CardImpl {

    public ChiefJimHopper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Chief Jim Hopper attacks, investigate once for each nontoken attacking creature.
        this.addAbility(new AttacksTriggeredAbility(new ChiefJimHopperEffect()));

        // Friends forever
        this.addAbility(FriendsForeverAbility.getInstance());
    }

    private ChiefJimHopper(final ChiefJimHopper card) {
        super(card);
    }

    @Override
    public ChiefJimHopper copy() {
        return new ChiefJimHopper(this);
    }
}

class ChiefJimHopperEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    ChiefJimHopperEffect() {
        super(Outcome.Benefit);
        staticText = "investigate once for each nontoken attacking creature";
    }

    private ChiefJimHopperEffect(final ChiefJimHopperEffect effect) {
        super(effect);
    }

    @Override
    public ChiefJimHopperEffect copy() {
        return new ChiefJimHopperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int attackers = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
        return attackers > 0 && new InvestigateEffect(attackers).apply(game, source);
    }
}
