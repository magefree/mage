package mage.cards.j;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JayaVeneratedFiremage extends CardImpl {

    public JayaVeneratedFiremage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JAYA);
        this.setStartingLoyalty(5);

        // If another red source you control would deal damage to a permanent or player, it deals that much damage plus 1 to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(new JayaVeneratedFiremageEffect()));

        // -2: Jaya, Venerated Firemage deals 2 damage tot any target.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(2), -2);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private JayaVeneratedFiremage(final JayaVeneratedFiremage card) {
        super(card);
    }

    @Override
    public JayaVeneratedFiremage copy() {
        return new JayaVeneratedFiremage(this);
    }
}

class JayaVeneratedFiremageEffect extends ReplacementEffectImpl {

    JayaVeneratedFiremageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If another red source you control would deal damage to a permanent or player, " +
                "it deals that much damage plus 1 to that permanent or player instead.";
    }

    private JayaVeneratedFiremageEffect(final JayaVeneratedFiremageEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            MageObject sourceObject;
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (sourcePermanent == null) {
                sourceObject = game.getObject(event.getSourceId());
            } else {
                sourceObject = sourcePermanent;
            }
            return sourceObject != null && sourceObject.getColor(game).isRed()
                    && !sourceObject.getId().equals(source.getSourceId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }

    @Override
    public JayaVeneratedFiremageEffect copy() {
        return new JayaVeneratedFiremageEffect(this);
    }

}
