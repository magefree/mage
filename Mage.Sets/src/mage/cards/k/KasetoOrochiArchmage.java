
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KasetoOrochiArchmage extends CardImpl {

    public KasetoOrochiArchmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}{U}: Target creature can't be blocked this turn. If that creature is a Snake, it gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KasetoEffect(), new ManaCostsImpl<>("{G}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KasetoOrochiArchmage(final KasetoOrochiArchmage card) {
        super(card);
    }

    @Override
    public KasetoOrochiArchmage copy() {
        return new KasetoOrochiArchmage(this);
    }
}

class KasetoEffect extends OneShotEffect {

    public KasetoEffect() {
        super(Outcome.BoostCreature);
        staticText = "Target creature can't be blocked this turn. If that creature is a Snake, it gets +2/+2 until end of turn";
    }

    public KasetoEffect(final KasetoEffect effect) {
        super(effect);
    }

    @Override
    public KasetoEffect copy() {
        return new KasetoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            game.addEffect(new CantBeBlockedTargetEffect(Duration.EndOfTurn), source);
            if (permanent.hasSubtype(SubType.SNAKE, game)) {
                game.addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }

}