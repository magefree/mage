package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class VoidstoneGargoyle extends CardImpl {

    public VoidstoneGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.GARGOYLE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // As Voidstone Gargoyle enters the battlefield, name a nonland card.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME)));
        // The named card can't be cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VoidstoneGargoyleReplacementEffect1()));
        // Activated abilities of sources with the chosen name can't be activated.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VoidstoneGargoyleRuleModifyingEffect2()));
    }

    private VoidstoneGargoyle(final VoidstoneGargoyle card) {
        super(card);
    }

    @Override
    public VoidstoneGargoyle copy() {
        return new VoidstoneGargoyle(this);
    }
}

class VoidstoneGargoyleReplacementEffect1 extends ContinuousRuleModifyingEffectImpl {

    public VoidstoneGargoyleReplacementEffect1() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Spells with the chosen name can't be cast";
    }

    private VoidstoneGargoyleReplacementEffect1(final VoidstoneGargoyleReplacementEffect1 effect) {
        super(effect);
    }

    @Override
    public VoidstoneGargoyleReplacementEffect1 copy() {
        return new VoidstoneGargoyleReplacementEffect1(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast a spell with that name (" + mageObject.getName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        return CardUtil.haveSameNames(object, cardName, game);
    }

}

class VoidstoneGargoyleRuleModifyingEffect2 extends ContinuousRuleModifyingEffectImpl {

    public VoidstoneGargoyleRuleModifyingEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Activated abilities of sources with the chosen name can't be activated";
    }

    private VoidstoneGargoyleRuleModifyingEffect2(final VoidstoneGargoyleRuleModifyingEffect2 effect) {
        super(effect);
    }

    @Override
    public VoidstoneGargoyleRuleModifyingEffect2 copy() {
        return new VoidstoneGargoyleRuleModifyingEffect2(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't activate abilities of sources with that name (" + mageObject.getName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        return CardUtil.haveSameNames(object, cardName, game);
    }
}
