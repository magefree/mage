package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author dragonfyre23
 *
 * cost increase effect based on EidolonOfObstruction
 */
public final class ExiledDoomsayer extends CardImpl {

    public ExiledDoomsayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // All morph costs cost {2} more.
        this.addAbility(new SimpleStaticAbility(new ExiledDoomsayerEffect()));

    }

    public ExiledDoomsayer(final mage.cards.e.ExiledDoomsayer card) {
        super(card);
    }

    @Override
    public mage.cards.e.ExiledDoomsayer copy() {
        return new mage.cards.e.ExiledDoomsayer(this);
    }
}

class ExiledDoomsayerEffect extends CostModificationEffectImpl {

    ExiledDoomsayerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, CostModificationType.INCREASE_COST);
        staticText = "All morph costs cost {2} more.";
    }

    private ExiledDoomsayerEffect(ExiledDoomsayerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Permanent permanent = game.getPermanent(abilityToModify.getSourceId());
        if (permanent == null) {//verify permanent exists
            return false;
        }
        
        if ((abilityToModify instanceof MorphAbility)) {//if ability is Morph
            return true;//increase cost
        }
        else {
            return false;//don't increase cost
        }
    }

    @Override
    public ExiledDoomsayerEffect copy() {
        return new ExiledDoomsayerEffect(this);
    }
}
