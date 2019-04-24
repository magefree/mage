
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class EliteScaleguard extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with a +1/+1 counter on it");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(new CounterPredicate(CounterType.P1P1));
        filter2.add(DefendingPlayerControlsPredicate.instance);
    }

    public EliteScaleguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Elite Scaleguard enters the battlefield, bolster 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BolsterEffect(2)));

        // Whenever a creature you control with a +1/+1 counter on it attacks, tap target creature defending player controls.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(new EliteScaleguardTapEffect(), false, filter, true);
        ability.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability);
    }

    public EliteScaleguard(final EliteScaleguard card) {
        super(card);
    }

    @Override
    public EliteScaleguard copy() {
        return new EliteScaleguard(this);
    }
}

class EliteScaleguardTapEffect extends TapTargetEffect {

    EliteScaleguardTapEffect() {
        super();
    }

    EliteScaleguardTapEffect(final EliteScaleguardTapEffect effect) {
        super(effect);
    }

    @Override
    public EliteScaleguardTapEffect copy() {
        return new EliteScaleguardTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.tap(game);
            return true;
        }
        return false;
    }
}
