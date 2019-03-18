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
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavagerWurm extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature you don't control");
    private static final FilterPermanent filter2
            = new FilterPermanent("land with an activated ability that isn't a mana ability");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter2.add(RavagerWurmPredicate.instance);
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
        ability.addTarget(new TargetPermanent(filter));
        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(1);

        // • Destroy target land with an activated ability that isn't a mana ability.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter2));
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
        if (input == null || !input.isLand()) {
            return false;
        }
        for (Ability ability : input.getAbilities()) {
            if (ability.getAbilityType() == AbilityType.ACTIVATED) {
                return true;
            }
        }
        return false;
    }
}