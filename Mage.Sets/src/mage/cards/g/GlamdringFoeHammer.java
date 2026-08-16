package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author miesma
 */
public final class GlamdringFoeHammer extends AdventureCard {

    public GlamdringFoeHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, new CardType[]{CardType.SORCERY}, "{2}" , "Gleam of Death", "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Instant and sorcery spells you cast cost {X} less to cast, where X is equipped creature’s power.
        this.addAbility(new SimpleStaticAbility(new GlamdringFoeHammerEffect())
                .addHint(GlamdringFoeHammerEffect.getHint()));


        // Equip {2}
        this.addAbility(new EquipAbility(2, false));

        // Gleam of Death
        // Mill six cards, then put all instant and sorcery cards from among them into your hand.
        this.getSpellCard().getSpellAbility().addEffect(new GleamofDeathEffect());

        this.finalizeAdventure();
    }

    private GlamdringFoeHammer(final GlamdringFoeHammer card) {
        super(card);
    }

    @Override
    public GlamdringFoeHammer copy() { return new GlamdringFoeHammer(this); }

}

class GlamdringFoeHammerEffect extends CostModificationEffectImpl {

    private static final DynamicValue xValue = new EquippedCreaturesPowerDynamicValue();
    private static final Hint hint = new ValueHint("equipped creature's power", xValue);

    GlamdringFoeHammerEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Instant and sorcery spells you cast cost {X} less to cast, where X is equipped creature's power";
    }

    private GlamdringFoeHammerEffect(final GlamdringFoeHammerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, xValue.calculate(game, source, this));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.isControlledBy(source.getControllerId())
                && ((SpellAbility) abilityToModify).getCharacteristics(game).isInstantOrSorcery(game)
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public GlamdringFoeHammerEffect copy() {
        return new GlamdringFoeHammerEffect(this);
    }

    public static Hint getHint() {
        return hint;
    }
}

class EquippedCreaturesPowerDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent equipment = game.getPermanent(sourceAbility.getSourceId());
        int xValue = 0;
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipedCreature = game.getPermanent(equipment.getAttachedTo());
            if (equipedCreature != null) {
                //107.1b If a calculation that would determine the result of an effect yields a negative number, zero is used instead
                xValue = equipedCreature.getPower().getValue();
                if (xValue < 0) {
                    return 0;
                }
            }
        }
        return xValue;
    }

    @Override
    public EquippedCreaturesPowerDynamicValue copy() {
        return new EquippedCreaturesPowerDynamicValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "equipped creature's power";
    }
}

class GleamofDeathEffect extends OneShotEffect {

    GleamofDeathEffect() {
        super(Outcome.DrawCard);
        staticText = "Mill six cards, then put all instant and sorcery cards from among them into your hand";
    }

    private GleamofDeathEffect(final GleamofDeathEffect effect) {
        super(effect);
    }

    @Override
    public GleamofDeathEffect copy() {
        return new GleamofDeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(6, source, game);
        cards.retainZone(Zone.GRAVEYARD, game);
        for (Card card : cards.getCards(game)) {
            //Keep all instants and sorceries
            if (!card.isInstantOrSorcery()) {
                cards.remove(card);
            }
        }
        player.moveCardsToHandWithInfo(cards, source, game, true);
        return true;
    }
}


