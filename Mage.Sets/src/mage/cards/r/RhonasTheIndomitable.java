package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class RhonasTheIndomitable extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public RhonasTheIndomitable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Rhonas, the Indomitable can't attack or block unless you control another creature with power 4 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RhonasTheIndomitableRestrictionEffect()));

        // {2}{G}: Another target creature gets +2/+0 and gains trample until end of turn.
        Effect effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        effect.setText("Another target creature gets +2/+0");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{2}{G}"));
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private RhonasTheIndomitable(final RhonasTheIndomitable card) {
        super(card);
    }

    @Override
    public RhonasTheIndomitable copy() {
        return new RhonasTheIndomitable(this);
    }
}

class RhonasTheIndomitableRestrictionEffect extends RestrictionEffect {

    public RhonasTheIndomitableRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless you control another creature with power 4 or greater";
    }

    public RhonasTheIndomitableRestrictionEffect(final RhonasTheIndomitableRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public RhonasTheIndomitableRestrictionEffect copy() {
        return new RhonasTheIndomitableRestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
        filter.add(AnotherPredicate.instance);
        if (permanent.getId().equals(source.getSourceId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int permanentsOnBattlefield = game.getBattlefield().count(filter, source.getControllerId(), source, game);
                return permanentsOnBattlefield < 1; // is this correct?
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}
