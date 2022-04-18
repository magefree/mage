
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ShaperParasite extends CardImpl {

    public ShaperParasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Morph {2}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{U}")));
        // When Shaper Parasite is turned face up, target creature gets +2/-2 or -2/+2 until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new ShaperParasiteEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ShaperParasite(final ShaperParasite card) {
        super(card);
    }

    @Override
    public ShaperParasite copy() {
        return new ShaperParasite(this);
    }
}

class ShaperParasiteEffect extends ContinuousEffectImpl {

    private int power;
    private int toughness;

    public ShaperParasiteEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = 2;
        this.toughness = -2;
        this.staticText = "target creature gets +2/-2 or -2/+2 until end of turn";
    }

    public ShaperParasiteEffect(final ShaperParasiteEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public ShaperParasiteEffect copy() {
        return new ShaperParasiteEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player player = game.getPlayer(source.getControllerId());
        String message = "Should the target creature get -2/+2 instead of +2/-2?";
        if (player != null && player.chooseUse(Outcome.Neutral, message, source, game)) {
            this.power *= -1;
            this.toughness *= -1;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            target.addPower(power);
            target.addToughness(toughness);
            return true;
        }
        return false;
    }
}
