package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CollectiveInferno extends CardImpl {

    public CollectiveInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // As this enchantment enters, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // Double all damage that sources you control of the chosen type would deal.
        this.addAbility(new SimpleStaticAbility(new CollectiveInfernoEffect()));
    }

    private CollectiveInferno(final CollectiveInferno card) {
        super(card);
    }

    @Override
    public CollectiveInferno copy() {
        return new CollectiveInferno(this);
    }
}


class CollectiveInfernoEffect extends ReplacementEffectImpl {

    CollectiveInfernoEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "double all damage that sources you control of the chosen type would deal";
    }

    private CollectiveInfernoEffect(final CollectiveInfernoEffect effect) {
        super(effect);
    }

    @Override
    public CollectiveInfernoEffect copy() {
        return new CollectiveInfernoEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (subType == null) {
            return false;
        }
        MageObject mageObject = game.getObject(event.getSourceId());
        return mageObject != null && mageObject.hasSubtype(subType, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
