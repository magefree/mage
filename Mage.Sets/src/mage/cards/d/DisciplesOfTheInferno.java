package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisciplesOfTheInferno extends CardImpl {

    public DisciplesOfTheInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setRed(true);
        this.nightCard = true;

        // Prowess
        this.addAbility(new ProwessAbility());

        // If a noncreature source you control would deal damage to a creature, battle, or opponent, it deals that much damage plus 2 instead.
        this.addAbility(new SimpleStaticAbility(new DisciplesOfTheInfernoEffect()));
    }

    private DisciplesOfTheInferno(final DisciplesOfTheInferno card) {
        super(card);
    }

    @Override
    public DisciplesOfTheInferno copy() {
        return new DisciplesOfTheInferno(this);
    }
}

class DisciplesOfTheInfernoEffect extends ReplacementEffectImpl {

    DisciplesOfTheInfernoEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "if a noncreature source you control would deal damage " +
                "to a creature, battle, or opponent, it deals that much damage plus 2 instead";
    }

    private DisciplesOfTheInfernoEffect(final DisciplesOfTheInfernoEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.hasOpponent(event.getTargetId(), game)
                && Optional
                .of(event.getTargetId())
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(permanent -> !permanent.isCreature(game) && !permanent.isBattle(game))
                .orElse(true)) {
            return false;
        }
        MageObject sourceObject;
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (sourcePermanent == null) {
            sourceObject = game.getObject(event.getSourceId());
        } else {
            sourceObject = sourcePermanent;
        }
        return sourceObject != null
                && !sourceObject.isCreature(game)
                && event.getAmount() > 0;
    }

    @Override
    public DisciplesOfTheInfernoEffect copy() {
        return new DisciplesOfTheInfernoEffect(this);
    }
}
