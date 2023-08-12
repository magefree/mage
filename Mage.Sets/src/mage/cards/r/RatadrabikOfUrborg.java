package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author rockydirtbag
 */
public final class RatadrabikOfUrborg extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Zombies you control");

    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("another legendary creature you control");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
        filter2.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    public RatadrabikOfUrborg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new GenericManaCost(2), false));

        // Other zombies you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever another legendary creature you control dies, create a token that's a copy of that creature,
        // except it's not legendary and it's a 2/2 black Zombie in addition to its other colors and types.
        this.addAbility(new DiesCreatureTriggeredAbility(new RatadrabikOfUrborgEffect(),false, filter2, true));

    }

    private RatadrabikOfUrborg(final RatadrabikOfUrborg card) {
        super(card);
    }

    @Override
    public RatadrabikOfUrborg copy() {
        return new RatadrabikOfUrborg(this);
    }
}

class RatadrabikOfUrborgEffect extends OneShotEffect {

    public RatadrabikOfUrborgEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of that creature, " +
                "except it's not legendary and it's a 2/2 black Zombie in addition to its other colors and types.";
    }

    public RatadrabikOfUrborgEffect(final RatadrabikOfUrborgEffect effect) {
        super(effect);
    }

    @Override
    public RatadrabikOfUrborgEffect copy() {
        return new RatadrabikOfUrborgEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent copyFrom = targetPointer.getFirstTargetPermanentOrLKI(game, source);
        if(controller == null || copyFrom == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(controller.getId(), null, false,1,false,false,null,2,2,false);
        effect.setAdditionalSubType(SubType.ZOMBIE);
        effect.setIsntLegendary(true);
        effect.setTargetPointer(new FixedTarget(copyFrom.getId(),game));
        ObjectColor colors = copyFrom.getColor();
        effect.setOnlyColor(colors.union(new ObjectColor("B")));
        effect.apply(game, source);
        return true;
    }
}
