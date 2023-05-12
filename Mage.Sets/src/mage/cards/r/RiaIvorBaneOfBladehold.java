package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.*;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PhyrexianMiteToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author huangn
 */
public final class RiaIvorBaneOfBladehold extends CardImpl {

    public RiaIvorBaneOfBladehold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Battle cry
        this.addAbility(new BattleCryAbility());

        // At the beginning of combat on your turn, the next time target creature would deal combat damage to one or more players this combat, prevent that damage. If damage is prevented this way, create that many 1/1 colorless Phyrexian Mite artifact creature tokens with toxic 1 and "This creature can't block."
        Ability ability = new BeginningOfCombatTriggeredAbility(new RiaIvorBaneOfBladeholdEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RiaIvorBaneOfBladehold(final RiaIvorBaneOfBladehold card) {
        super(card);
    }

    @Override
    public RiaIvorBaneOfBladehold copy() {
        return new RiaIvorBaneOfBladehold(this);
    }
}

class RiaIvorBaneOfBladeholdEffect extends PreventionEffectImpl {

    public RiaIvorBaneOfBladeholdEffect() {
        super(Duration.EndOfCombat, Integer.MAX_VALUE, true, false);
        this.staticText = "the next time target creature would deal combat damage to one or more players this combat, prevent that damage. If damage is prevented this way, create that many 1/1 colorless Phyrexian Mite artifact creature tokens with toxic 1 and \"This creature can't block.\"";
    }

    public RiaIvorBaneOfBladeholdEffect(final RiaIvorBaneOfBladeholdEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        this.used = true;
        this.discard();
        if (preventionData.getPreventedDamage() > 0) {
            Token token = new PhyrexianMiteToken();
            token.putOntoBattlefield(preventionData.getPreventedDamage(), game, source);
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            return targetCreature != null && targetCreature.getId().equals(event.getSourceId());
        }
        return false;
    }

    @Override
    public RiaIvorBaneOfBladeholdEffect copy() {
        return new RiaIvorBaneOfBladeholdEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }
}