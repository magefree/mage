package mage.cards.o;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ManaChoice;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OpenTheOmenpaths extends CardImpl {

    public OpenTheOmenpaths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Choose one —
        // • Add two mana of any one color and two mana of any other color. Spend this mana only to cast creature or enchantment spells.
        this.getSpellAbility().addEffect(new OpenTheOmenpathsEffect());

        // • Creatures you control get +1/+0 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostControlledEffect(1, 0, Duration.EndOfTurn)));
    }

    private OpenTheOmenpaths(final OpenTheOmenpaths card) {
        super(card);
    }

    @Override
    public OpenTheOmenpaths copy() {
        return new OpenTheOmenpaths(this);
    }
}

class OpenTheOmenpathsEffect extends OneShotEffect {

    OpenTheOmenpathsEffect() {
        super(Outcome.Benefit);
        staticText = "add two mana of any one color and two mana of any other color. " +
                "Spend this mana only to cast creature or enchantment spells";
    }

    private OpenTheOmenpathsEffect(final OpenTheOmenpathsEffect effect) {
        super(effect);
    }

    @Override
    public OpenTheOmenpathsEffect copy() {
        return new OpenTheOmenpathsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Mana mana = ManaChoice.chooseTwoDifferentColors(player, game);
        mana.add(mana);
        ConditionalMana conditionalMana = new ConditionalMana(mana);
        conditionalMana.addCondition(new OpenTheOmenpathsCondition());
        player.getManaPool().addMana(conditionalMana, game, source);
        return true;
    }
}

class OpenTheOmenpathsCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility)) {
            return false;
        }
        MageObject object = game.getObject(source);
        return object != null && (object.isCreature(game) || object.isEnchantment(game));
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
