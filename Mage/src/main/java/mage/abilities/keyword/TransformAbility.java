package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class TransformAbility extends SimpleStaticAbility {

    // this state value controls if a permanent enters the battlefield already transformed
    public static final String VALUE_KEY_ENTER_TRANSFORMED = "EnterTransformed";

    public TransformAbility() {
        super(Zone.BATTLEFIELD, new TransformEffect());
    }

    private TransformAbility(final TransformAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new TransformAbility(this);
    }

    @Override
    public String getRule() {
        return "";
    }

    public static void transform(Permanent permanent, Card sourceCard, Game game, Ability source) {

        if (sourceCard == null) {
            return;
        }

        permanent.setName(sourceCard.getName());
        permanent.getColor(game).setColor(sourceCard.getColor(game));
        permanent.getManaCost().clear();
        permanent.getManaCost().add(sourceCard.getManaCost());
        permanent.getCardType().clear();
        for (CardType type : sourceCard.getCardType()) {
            permanent.addCardType(type);
        }
        permanent.removeAllSubTypes(game);
        permanent.copySubTypesFrom(game, sourceCard);
        permanent.getSuperType().clear();
        for (SuperType type : sourceCard.getSuperType()) {
            permanent.addSuperType(type);
        }
        permanent.setExpansionSetCode(sourceCard.getExpansionSetCode());
        permanent.getAbilities().clear();
        for (Ability ability : sourceCard.getAbilities()) {
            // source == null -- call from init card (e.g. own abilities)
            // source != null -- from apply effect
            permanent.addAbility(ability, source == null ? permanent.getId() : source.getSourceId(), game);
        }
        permanent.getPower().modifyBaseValue(sourceCard.getPower().getValue());
        permanent.getToughness().modifyBaseValue(sourceCard.getToughness().getValue());
        permanent.setTransformable(sourceCard.isTransformable());
    }
}

class TransformEffect extends ContinuousEffectImpl {

    TransformEffect() {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.NA, Outcome.BecomeCreature);
        staticText = "";
    }

    private TransformEffect(final TransformEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (permanent == null) {
            return false;
        }

        if (permanent.isCopy()) { // copies can't transform
            return true;
        }

        if (!permanent.isTransformed()) {
            // keep original card
            return true;
        }

        Card card = permanent.getSecondCardFace();

        if (card == null) {
            return false;
        }

        TransformAbility.transform(permanent, card, game, source);

        return true;

    }

    @Override
    public TransformEffect copy() {
        return new TransformEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "";
    }
}
