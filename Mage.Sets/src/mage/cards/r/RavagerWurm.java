package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavagerWurm extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("land with an activated ability that isn't a mana ability");

    static {
        filter.add(RavagerWurmPredicate.instance);
    }

    public RavagerWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Riot
        this.addAbility(new RiotAbility());

        // When Ravager Wurm enters the battlefield, choose up to one —
        // • Ravager Wurm fights target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new FightTargetSourceEffect().setText("{this} fights target creature you don't control"), false
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(1);

        // • Destroy target land with an activated ability that isn't a mana ability.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private RavagerWurm(final RavagerWurm card) {
        super(card);
    }

    @Override
    public RavagerWurm copy() {
        return new RavagerWurm(this);
    }
}

enum RavagerWurmPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input != null && input.isLand(game)
                && input
                .getAbilities(game)
                .stream()
                .map(Ability::getAbilityType)
                .anyMatch(AbilityType.ACTIVATED::equals);
    }
}
