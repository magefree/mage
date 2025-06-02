package mage.cards.i;

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
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class IzzetStaticaster extends CardImpl {

    public IzzetStaticaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flash (You may cast this spell any time you could cast an instant.)
        this.addAbility(FlashAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: Izzet Staticaster deals 1 damage to target creature and each other creature with the same name as that creature.
        Ability ability = new SimpleActivatedAbility(new IzzetStaticasterDamageEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private IzzetStaticaster(final IzzetStaticaster card) {
        super(card);
    }

    @Override
    public IzzetStaticaster copy() {
        return new IzzetStaticaster(this);
    }
}

class IzzetStaticasterDamageEffect extends OneShotEffect {

    IzzetStaticasterDamageEffect() {
        super(Outcome.Exile);
        this.staticText = "{this} deals 1 damage to target creature and each other creature with the same name as that creature";
    }

    private IzzetStaticasterDamageEffect(final IzzetStaticasterDamageEffect effect) {
        super(effect);
    }

    @Override
    public IzzetStaticasterDamageEffect copy() {
        return new IzzetStaticasterDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetPermanent == null) {
            return false;
        }
        Set<Permanent> set = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(permanent -> permanent.sharesName(targetPermanent, game))
                .collect(Collectors.toSet());
        set.add(targetPermanent);
        for (Permanent creature : set) {
            creature.damage(1, source, game);
        }
        return true;
    }
}
