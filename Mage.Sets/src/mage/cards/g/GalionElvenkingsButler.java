package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author miesma
 */
public final class GalionElvenkingsButler extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GalionElvenkingsButler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Galion attacks, choose up to one other target creature you control.
        // Its base power and toughness become equal to Galion’s power and toughness until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new GalionElvenkingsButlerBoostEffect());
        ability.addTarget(new TargetPermanent(0,1, filter));
        this.addAbility(ability);
    }

    private GalionElvenkingsButler(final GalionElvenkingsButler card) {
        super(card);
    }

    @Override
    public GalionElvenkingsButler copy() {
        return new GalionElvenkingsButler(this);
    }
}

class GalionElvenkingsButlerBoostEffect extends OneShotEffect {

    GalionElvenkingsButlerBoostEffect() {
        super(Outcome.BoostCreature);
        staticText = "choose up to one other target creature you control. " +
                "Its base power and toughness become equal to Galion’s power and toughness until end of turn.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Looking back in time
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (permanent != null && target != null) {
            game.addEffect(new SetBasePowerToughnessTargetEffect(
                    permanent.getPower().getValue(),
                    permanent.getToughness().getValue(),
                    Duration.EndOfTurn)
                    .setTargetPointer(this.getTargetPointer()),source);
            return true;
        }
        return false;
    }

    @Override
    public GalionElvenkingsButlerBoostEffect copy() {
        return new GalionElvenkingsButlerBoostEffect();
    }
}
