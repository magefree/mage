
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IzzetStaticaster extends CardImpl {

    public IzzetStaticaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flash (You may cast this spell any time you could cast an instant.)
        this.addAbility(FlashAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: Izzet Staticaster deals 1 damage to target creature and each other creature with the same name as that creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new IzzetStaticasterDamageEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public IzzetStaticaster(final IzzetStaticaster card) {
        super(card);
    }

    @Override
    public IzzetStaticaster copy() {
        return new IzzetStaticaster(this);
    }
}

class IzzetStaticasterDamageEffect extends OneShotEffect {

    public IzzetStaticasterDamageEffect() {
        super(Outcome.Exile);
        this.staticText = "{this} deals 1 damage to target creature and each other creature with the same name as that creature";
    }

    public IzzetStaticasterDamageEffect(final IzzetStaticasterDamageEffect effect) {
        super(effect);
    }

    @Override
    public IzzetStaticasterDamageEffect copy() {
        return new IzzetStaticasterDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetPermanent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            if (targetPermanent.getName().isEmpty()) {
                filter.add(new PermanentIdPredicate(targetPermanent.getId()));  // if no name (face down creature) only the creature itself is selected
            } else {
                filter.add(new NamePredicate(targetPermanent.getName()));
            }
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                permanent.damage(1, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
