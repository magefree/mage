package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformTargetEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElvishVatkeeper extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(
            SubType.INCUBATOR, "Incubator token you control"
    );

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public ElvishVatkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Elvish Vatkeeper enters the battlefield, incubate 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IncubateEffect(2)));

        // {5}: Transform target Incubator token you control. Double the number of +1/+1 counters on it.
        Ability ability = new SimpleActivatedAbility(new TransformTargetEffect(), new GenericManaCost(5));
        ability.addEffect(new ElvishVatkeeperEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ElvishVatkeeper(final ElvishVatkeeper card) {
        super(card);
    }

    @Override
    public ElvishVatkeeper copy() {
        return new ElvishVatkeeper(this);
    }
}

class ElvishVatkeeperEffect extends OneShotEffect {

    ElvishVatkeeperEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of +1/+1 counters on it";
    }

    private ElvishVatkeeperEffect(final ElvishVatkeeperEffect effect) {
        super(effect);
    }

    @Override
    public ElvishVatkeeperEffect copy() {
        return new ElvishVatkeeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && permanent.addCounters(CounterType.P1P1.createInstance(
                permanent.getCounters(game).getCount(CounterType.P1P1)
        ), source.getControllerId(), source, game);
    }
}
