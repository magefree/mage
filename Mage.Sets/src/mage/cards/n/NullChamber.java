package mage.cards.n;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class NullChamber extends CardImpl {

    public NullChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        
        this.addSuperType(SuperType.WORLD);

        // As Null Chamber enters the battlefield, you and an opponent each name a card other than a basic land card.
        // The named cards can't be played.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NOT_BASIC_LAND_NAME)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NullChamberReplacementEffect()));
        
    }

    public NullChamber(final NullChamber card) {
        super(card);
    }

    @Override
    public NullChamber copy() {
        return new NullChamber(this);
    }
}


class NullChamberReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public NullChamberReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.AIDontUseIt);
        staticText = "The named cards can't be played";
    }

    public NullChamberReplacementEffect(final NullChamberReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NullChamberReplacementEffect copy() {
        return new NullChamberReplacementEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast a spell with that name (" + mageObject.getLogName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
            MageObject object = game.getObject(event.getSourceId());
            return (object != null 
                    && object.getName().equals(game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY)));
    }
}