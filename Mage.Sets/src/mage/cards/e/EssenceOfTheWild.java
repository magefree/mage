package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward
 */
public final class EssenceOfTheWild extends CardImpl {

    public EssenceOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Creatures you control enter the battlefield as a copy of Essence of the Wild.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EssenceOfTheWildEffect()));
    }

    private EssenceOfTheWild(final EssenceOfTheWild card) {
        super(card);
    }

    @Override
    public EssenceOfTheWild copy() {
        return new EssenceOfTheWild(this);
    }
}

class EssenceOfTheWildEffect extends ReplacementEffectImpl {

    public EssenceOfTheWildEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Copy, false);
        staticText = "Creatures you control enter the battlefield as a copy of {this}";
    }

    private EssenceOfTheWildEffect(final EssenceOfTheWildEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent perm = ((EntersTheBattlefieldEvent) event).getTarget();
        return perm != null && perm.isCreature(game) && perm.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            game.copyPermanent(sourceObject, event.getTargetId(), source, null);
        }
        return false;
    }

    @Override
    public EssenceOfTheWildEffect copy() {
        return new EssenceOfTheWildEffect(this);
    }

}
