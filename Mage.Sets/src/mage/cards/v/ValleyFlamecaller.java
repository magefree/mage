package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValleyFlamecaller extends CardImpl {

    public ValleyFlamecaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If a Lizard, Mouse, Otter, or Raccoon you control would deal damage to a permanent or player, it deals that much damage plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new ValleyFlamecallerEffect()));
    }

    private ValleyFlamecaller(final ValleyFlamecaller card) {
        super(card);
    }

    @Override
    public ValleyFlamecaller copy() {
        return new ValleyFlamecaller(this);
    }
}

class ValleyFlamecallerEffect extends ReplacementEffectImpl {

    ValleyFlamecallerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a Lizard, Mouse, Otter, or Raccoon you control would deal damage " +
                "to a permanent or player, it deals that much damage plus 1 instead";
    }

    private ValleyFlamecallerEffect(final ValleyFlamecallerEffect effect) {
        super(effect);
    }

    @Override
    public ValleyFlamecallerEffect copy() {
        return new ValleyFlamecallerEffect(this);
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
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && (permanent.hasSubtype(SubType.LIZARD, game)
                || permanent.hasSubtype(SubType.MOUSE, game)
                || permanent.hasSubtype(SubType.OTTER, game)
                || permanent.hasSubtype(SubType.RACCOON, game));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}
