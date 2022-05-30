
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DragonsoulKnight extends CardImpl {

    public DragonsoulKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {W}{U}{B}{R}{G}: Until end of turn, Dragonsoul Knight becomes a Dragon, gets +5/+3, and gains flying and trample.
        Ability ability = new SimpleActivatedAbility(new DragonsoulKnightEffect(), new ManaCostsImpl<>("{W}{U}{B}{R}{G}"));
        Effect effect = new BoostSourceEffect(5, 3, Duration.EndOfTurn);
        effect.setText(", gets +5/+3");
        ability.addEffect(effect);
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(", and gains flying");
        ability.addEffect(effect);
        effect = new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and trample");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private DragonsoulKnight(final DragonsoulKnight card) {
        super(card);
    }

    @Override
    public DragonsoulKnight copy() {
        return new DragonsoulKnight(this);
    }

    private static class DragonsoulKnightEffect extends ContinuousEffectImpl {

        private DragonsoulKnightEffect() {
            super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.BecomeCreature);
            staticText = "Until end of turn, {this} becomes a Dragon";
        }

        private DragonsoulKnightEffect(final DragonsoulKnightEffect effect) {
            super(effect);
        }

        @Override
        public DragonsoulKnightEffect copy() {
            return new DragonsoulKnightEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                return false;
            }
            permanent.removeAllCreatureTypes(game);
            permanent.addSubType(game, SubType.DRAGON);
            return true;
        }
    }
}
