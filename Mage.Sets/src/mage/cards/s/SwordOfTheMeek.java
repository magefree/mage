
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SwordOfTheMeek extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a 1/1 creature");

    static {
        filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, 1));
        filter.add(new ToughnessPredicate(ComparisonType.EQUAL_TO, 1));
    }

    public SwordOfTheMeek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 2, Duration.WhileOnBattlefield)));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
        // Whenever a 1/1 creature enters the battlefield under your control, you may return Sword of the Meek from your graveyard to the battlefield, then attach it to that creature.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.GRAVEYARD, new SwordOfTheMeekEffect(), filter, true, SetTargetPointer.PERMANENT, ""));
    }

    private SwordOfTheMeek(final SwordOfTheMeek card) {
        super(card);
    }

    @Override
    public SwordOfTheMeek copy() {
        return new SwordOfTheMeek(this);
    }
}

class SwordOfTheMeekEffect extends OneShotEffect {

    public SwordOfTheMeekEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may return {this} from your graveyard to the battlefield, then attach it to that creature";
    }

    public SwordOfTheMeekEffect(final SwordOfTheMeekEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfTheMeekEffect copy() {
        return new SwordOfTheMeekEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card equipment = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (equipment != null && controller != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            controller.moveCards(equipment, Zone.BATTLEFIELD, source, game);
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                return permanent.addAttachment(equipment.getId(), source, game);
            }
        }
        return false;
    }
}
