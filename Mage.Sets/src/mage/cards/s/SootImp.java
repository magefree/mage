
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class SootImp extends CardImpl {

    static final private FilterSpell filter = new FilterSpell("a nonblack spell");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public SootImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.IMP);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player casts a nonblack spell, that player loses 1 life.
        this.addAbility(new SpellCastAllTriggeredAbility(new SootImpEffect(), filter, false, SetTargetPointer.PLAYER));

    }

    private SootImp(final SootImp card) {
        super(card);
    }

    @Override
    public SootImp copy() {
        return new SootImp(this);
    }
}

class SootImpEffect extends OneShotEffect {

    public SootImpEffect() {
        super(Outcome.Neutral);
        this.staticText = "that player loses 1 life";
    }

    private SootImpEffect(final SootImpEffect effect) {
        super(effect);
    }

    @Override
    public SootImpEffect copy() {
        return new SootImpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player caster = game.getPlayer(targetPointer.getFirst(game, source));
        if (caster != null) {
            caster.loseLife(1, game, source, false);
            return true;
        }
        return false;
    }
}
