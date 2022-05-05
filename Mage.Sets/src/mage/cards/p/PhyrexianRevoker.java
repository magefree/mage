package mage.cards.p;

import java.util.Optional;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.util.CardUtil;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class PhyrexianRevoker extends CardImpl {

    public PhyrexianRevoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As Phyrexian Revoker enters the battlefield, name a nonland card.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME)));

        // Activated abilities of sources with the chosen name can't be activated.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhyrexianRevokerEffect2()));
    }

    private PhyrexianRevoker(final PhyrexianRevoker card) {
        super(card);
    }

    @Override
    public PhyrexianRevoker copy() {
        return new PhyrexianRevoker(this);
    }
}

class PhyrexianRevokerEffect2 extends ContinuousRuleModifyingEffectImpl {

    public PhyrexianRevokerEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Activated abilities of sources with the chosen name can't be activated";
    }

    public PhyrexianRevokerEffect2(final PhyrexianRevokerEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PhyrexianRevokerEffect2 copy() {
        return new PhyrexianRevokerEffect2(this);
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
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
            MageObject object = game.getObject(event.getSourceId()); // Can happen for special ability????
            if (object != null) {
                Optional<Ability> optAbility = object.getAbilities().get(event.getTargetId());
                if (optAbility.isPresent() && AbilityType.SPECIAL_ACTION == optAbility.get().getAbilityType()) {
                    return false;
                }
                String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
                return CardUtil.haveSameNames(object, cardName, game);
            }
        }
        return false;
    }
}
