
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class OrchardWarden extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another Treefolk creature");
    
    static {
        filter.add(SubType.TREEFOLK.getPredicate());
        filter.add(AnotherPredicate.instance);
    }
    
    public OrchardWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Whenever another Treefolk creature enters the battlefield under your control, you may gain life equal to that creature's toughness.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new OrchardWardenffect(), filter, true, SetTargetPointer.PERMANENT));
    }

    private OrchardWarden(final OrchardWarden card) {
        super(card);
    }

    @Override
    public OrchardWarden copy() {
        return new OrchardWarden(this);
    }
}

class OrchardWardenffect extends OneShotEffect {
    
    public OrchardWardenffect() {
        super(Outcome.GainLife);
        this.staticText = "you may gain life equal to that creature's toughness";
    }
    
    private OrchardWardenffect(final OrchardWardenffect effect) {
        super(effect);
    }
    
    @Override
    public OrchardWardenffect copy() {
        return new OrchardWardenffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && permanent != null) {
            controller.gainLife(permanent.getToughness().getValue(), game, source);
            return true;
        }
        return false;
    }
}
