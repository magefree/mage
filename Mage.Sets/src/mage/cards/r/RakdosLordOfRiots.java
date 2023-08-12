package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RakdosLordOfRiots extends CardImpl {

    public RakdosLordOfRiots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // You can't cast Rakdos, Lord of Riots unless an opponent lost life this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new RakdosLordOfRiotsCantCastEffect()).addHint(OpponentsLostLifeHint.instance));

        // Flying, trample
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());

        // Creature spells you cast cost {1} less to cast for each 1 life your opponents have lost this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RakdosLordOfRiotsCostReductionEffect()));
    }

    private RakdosLordOfRiots(final RakdosLordOfRiots card) {
        super(card);
    }

    @Override
    public RakdosLordOfRiots copy() {
        return new RakdosLordOfRiots(this);
    }
}

class RakdosLordOfRiotsCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public RakdosLordOfRiotsCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You can't cast this spell unless an opponent lost life this turn";
    }

    public RakdosLordOfRiotsCantCastEffect(final RakdosLordOfRiotsCantCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RakdosLordOfRiotsCantCastEffect copy() {
        return new RakdosLordOfRiotsCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            return OpponentsLostLifeCount.instance.calculate(game, source, this) == 0;
        }
        return false;
    }
}

class RakdosLordOfRiotsCostReductionEffect extends CostModificationEffectImpl {

    RakdosLordOfRiotsCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Creature spells you cast cost {1} less to cast for each 1 life your opponents have lost this turn";
    }

    RakdosLordOfRiotsCostReductionEffect(RakdosLordOfRiotsCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Ability spellAbility = abilityToModify;
        if (spellAbility != null) {
            int amount = OpponentsLostLifeCount.instance.calculate(game, source, this);
            CardUtil.reduceCost(spellAbility, amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.isControlledBy(source.getControllerId())) {
                Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
                if (spellCard != null) {
                    return spellCard.isCreature(game);
                }
            }
        }
        return false;
    }

    @Override
    public RakdosLordOfRiotsCostReductionEffect copy() {
        return new RakdosLordOfRiotsCostReductionEffect(this);
    }

}
