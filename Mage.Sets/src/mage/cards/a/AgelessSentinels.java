package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
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
        Ability ability = new BlocksSourceTriggeredAbility(new AgelessSentinelsEffect()).setTriggerPhrase("When {this} blocks, ");
        Effect effect = new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.WhileOnBattlefield);
        effect.setText(", and it loses defender. <i>(It's no longer a Wall. This effect lasts indefinitely.)</i>");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private AgelessSentinels(final AgelessSentinels card) {
        super(card);
    }

    @Override
    public AgelessSentinels copy() {
        return new AgelessSentinels(this);
    }
}

class AgelessSentinelsEffect extends ContinuousEffectImpl {

    public AgelessSentinelsEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.BecomeCreature);
        staticText = "it becomes a Bird Giant";
    }

    private AgelessSentinelsEffect(final AgelessSentinelsEffect effect) {
        super(effect);
    }

    @Override
    public AgelessSentinelsEffect copy() {
        return new AgelessSentinelsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        permanent.removeAllCreatureTypes(game);
        permanent.addSubType(game, SubType.BIRD, SubType.GIANT);
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }
}
