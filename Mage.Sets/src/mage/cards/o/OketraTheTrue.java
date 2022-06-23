package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WarriorVigilantToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class OketraTheTrue extends CardImpl {

    public OketraTheTrue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Oketra the True can't attack or block unless you control three or more creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OketraTheTrueRestrictionEffect()));

        // {3}{W}: Create a 1/1 white Warrior creature token with vigilance.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new WarriorVigilantToken()), new ManaCostsImpl<>("{3}{W}")));
    }

    private OketraTheTrue(final OketraTheTrue card) {
        super(card);
    }

    @Override
    public OketraTheTrue copy() {
        return new OketraTheTrue(this);
    }
}

class OketraTheTrueRestrictionEffect extends RestrictionEffect {

    public OketraTheTrueRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless you control at least three other creatures";
    }

    public OketraTheTrueRestrictionEffect(final OketraTheTrueRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public OketraTheTrueRestrictionEffect copy() {
        return new OketraTheTrueRestrictionEffect(this);
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
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(source.getControllerId()));
        if (permanent.getId().equals(source.getSourceId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int permanentsOnBattlefield = game.getBattlefield().count(filter, source.getControllerId(), source, game);
                return (ComparisonType.compare(permanentsOnBattlefield, ComparisonType.FEWER_THAN, 4));
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}
