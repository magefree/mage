
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class AgelessSentinels extends CardImpl {

    public AgelessSentinels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ageless Sentinels blocks, it becomes a Bird Giant, and it loses defender.
        Ability ability = new BlocksTriggeredAbility(new AgelessSentinelsEffect(), false, false, true);
        Effect effect = new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.WhileOnBattlefield);
        effect.setText("and it loses defender");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public AgelessSentinels(final AgelessSentinels card) {
        super(card);
    }

    @Override
    public AgelessSentinels copy() {
        return new AgelessSentinels(this);
    }

    private class AgelessSentinelsEffect extends ContinuousEffectImpl {

        public AgelessSentinelsEffect() {
            super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
            staticText = "it becomes a Bird Giant,";
        }

        public AgelessSentinelsEffect(final AgelessSentinelsEffect effect) {
            super(effect);
        }

        @Override
        public AgelessSentinelsEffect copy() {
            return new AgelessSentinelsEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                return false;
            }
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.getSubtype(game).clear();
                        permanent.getSubtype(game).add(SubType.BIRD, SubType.GIANT);
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.TypeChangingEffects_4;
        }
    }
}
