package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.SourceDidDamageWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class RedGuardianSuperSoldier extends CardImpl {

    private static final FilterPermanent filter
        = new FilterCreaturePermanent("creature an opponent controls that dealt damage this turn");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(RedGuardianSuperSoldierPredicate.instance);
    }

    public RedGuardianSuperSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Red Guardian enters, destroy target creature an opponent controls that dealt damage this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability, new SourceDidDamageWatcher());
    }

    private RedGuardianSuperSoldier(final RedGuardianSuperSoldier card) {
        super(card);
    }

    @Override
    public RedGuardianSuperSoldier copy() {
        return new RedGuardianSuperSoldier(this);
    }
}

enum RedGuardianSuperSoldierPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        SourceDidDamageWatcher watcher = game.getState().getWatcher(SourceDidDamageWatcher.class);
        return watcher != null && watcher.checkSource(input, game);
    }
}
