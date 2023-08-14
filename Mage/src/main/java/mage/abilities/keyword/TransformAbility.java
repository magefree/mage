package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.MageObjectAttribute;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.stack.Spell;
import mage.util.CardUtil;

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

    public static void transformPermanent(Permanent permanent, MageObject sourceCard, Game game, Ability source) {
        if (sourceCard == null) {
            return;
        }

        permanent.setTransformed(true);
        permanent.setName(sourceCard.getName());
        permanent.getColor(game).setColor(sourceCard.getColor(game));
        permanent.getManaCost().clear();
        permanent.getManaCost().add(sourceCard.getManaCost().copy());
        permanent.removeAllCardTypes(game);
        for (CardType type : sourceCard.getCardType(game)) {
            permanent.addCardType(game, type);
        }
        permanent.removeAllSubTypes(game);
        permanent.copySubTypesFrom(game, sourceCard);
        permanent.removeAllSuperTypes(game);
        for (SuperType type : sourceCard.getSuperType(game)) {
            permanent.addSuperType(game, type);
        }

        CardUtil.copySetAndCardNumber(permanent, sourceCard);

        permanent.getAbilities().clear();
        for (Ability ability : sourceCard.getAbilities()) {
            // source == null -- call from init card (e.g. own abilities)
            // source != null -- from apply effect
            permanent.addAbility(ability, source == null ? permanent.getId() : source.getSourceId(), game);
        }
        permanent.getPower().setModifiedBaseValue(sourceCard.getPower().getValue());
        permanent.getToughness().setModifiedBaseValue(sourceCard.getToughness().getValue());
        permanent.setStartingLoyalty(sourceCard.getStartingLoyalty());
        permanent.setStartingDefense(sourceCard.getStartingDefense());
    }

    public static Card transformCardSpellStatic(Card mainSide, Card otherSide, Game game) {
        // workaround to simulate transformed card on the stack (example: disturb ability)
        // prepare static attributes
        // TODO: must be removed after transform cards (one side) migrated to MDF engine (multiple sides)
        Card newCard = mainSide.copy();
        newCard.setName(otherSide.getName());

        // mana value must be from main side only
        newCard.getManaCost().clear();
        newCard.getManaCost().add(mainSide.getManaCost().copy());

        game.getState().getCardState(newCard.getId()).clearAbilities();
        for (Ability ability : otherSide.getAbilities()) {
            game.getState().addOtherAbility(newCard, ability);
        }
        newCard.getPower().setModifiedBaseValue(otherSide.getPower().getValue());
        newCard.getToughness().setModifiedBaseValue(otherSide.getToughness().getValue());

        return newCard;
    }

    public static void transformCardSpellDynamic(Spell spell, Card otherSide, Game game) {
        // workaround to simulate transformed card on the stack (example: disturb ability)
        // prepare dynamic attributes
        // TODO: must be removed after transform cards (one side) migrated to MDF engine (multiple sides)
        MageObjectAttribute moa = game.getState().getCreateMageObjectAttribute(spell.getCard(), game);
        moa.getColor().setColor(otherSide.getColor(game));
        moa.getCardType().clear();
        moa.getCardType().addAll(otherSide.getCardType(game));
        moa.getSuperType().clear();
        moa.getSuperType().addAll(otherSide.getSuperType(game));
        moa.getSubtype().clear();
        moa.getSubtype().addAll(otherSide.getSubtype(game));

        game.getState().getCardState(spell.getCard().getId()).clearAbilities();
        for (Ability ability : otherSide.getAbilities()) {
            game.getState().addOtherAbility(spell.getCard(), ability);
        }
    }
}

class TransformEffect extends ContinuousEffectImpl {

    TransformEffect() {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.BecomeCreature);
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

        MageObject card;
        if (permanent instanceof PermanentToken) {
            card = ((PermanentToken) permanent).getToken().getBackFace();
        } else {
            card = permanent.getSecondCardFace();
        }

        if (card == null) {
            return false;
        }

        TransformAbility.transformPermanent(permanent, card, game, source);

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
