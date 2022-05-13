package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author maxlebedev
 */
public final class SanctumPrelate extends CardImpl {

    public SanctumPrelate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Sanctum Prelate enters the battlefield, choose a number.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseNumberEffect()));

        // Noncreature spells with converted mana cost equal to the chosen number can't be cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SanctumPrelateReplacementEffect()));
    }

    private SanctumPrelate(final SanctumPrelate card) {
        super(card);
    }

    @Override
    public SanctumPrelate copy() {
        return new SanctumPrelate(this);
    }
}

class ChooseNumberEffect extends OneShotEffect {

    public ChooseNumberEffect() {
        super(Outcome.Detriment);
        staticText = "choose a number";
    }

    public ChooseNumberEffect(final ChooseNumberEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int numberChoice = controller.announceXMana(0, Integer.MAX_VALUE, "Choose a number.", game, source);
            game.getState().setValue(source.getSourceId().toString(), numberChoice);

            Permanent permanent = game.getPermanentEntering(source.getSourceId());
            if (permanent != null) {
                permanent.addInfo("chosen players", "<font color = 'blue'>Chosen Number: " + numberChoice + "</font>", game);

                game.informPlayers(permanent.getLogName() + ", chosen number: " + numberChoice);
            }
        }
        return true;
    }

    @Override
    public ChooseNumberEffect copy() {
        return new ChooseNumberEffect(this);
    }
}

class SanctumPrelateReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    Integer choiceValue;

    public SanctumPrelateReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Noncreature spells with mana value equal to the chosen number can't be cast";
    }

    public SanctumPrelateReplacementEffect(final SanctumPrelateReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SanctumPrelateReplacementEffect copy() {
        return new SanctumPrelateReplacementEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast a noncreature card with that mana value (" + mageObject.getIdName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        choiceValue = (Integer) game.getState().getValue(source.getSourceId().toString());
        Spell spell = game.getStack().getSpell(event.getTargetId());

        if (spell != null && !spell.isCreature(game)) {
            return spell.getManaValue() == choiceValue;
        }
        return false;
    }

}