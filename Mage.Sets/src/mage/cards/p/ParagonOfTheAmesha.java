package mage.cards.p;

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
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ParagonOfTheAmesha extends CardImpl {

    public ParagonOfTheAmesha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {W}{U}{B}{R}{G}: Until end of turn, Paragon of the Amesha becomes an Angel, gets +3/+3, and gains flying and lifelink.
        Ability ability = new SimpleActivatedAbility(new ParagonOfTheAmeshaEffect(), new ManaCostsImpl<>("{W}{U}{B}{R}{G}"));
        Effect effect = new BoostSourceEffect(3, 3, Duration.EndOfTurn);
        effect.setText(", gets +3/+3");
        ability.addEffect(effect);
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(", and gains flying");
        ability.addEffect(effect);
        effect = new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private ParagonOfTheAmesha(final ParagonOfTheAmesha card) {
        super(card);
    }

    @Override
    public ParagonOfTheAmesha copy() {
        return new ParagonOfTheAmesha(this);
    }

    private static class ParagonOfTheAmeshaEffect extends ContinuousEffectImpl {

        private ParagonOfTheAmeshaEffect() {
            super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.BecomeCreature);
            staticText = "Until end of turn, {this} becomes an Angel";
        }

        private ParagonOfTheAmeshaEffect(final ParagonOfTheAmeshaEffect effect) {
            super(effect);
        }

        @Override
        public ParagonOfTheAmeshaEffect copy() {
            return new ParagonOfTheAmeshaEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                return false;
            }
            permanent.removeAllCreatureTypes(game);
            permanent.addSubType(game, SubType.ANGEL);
            return true;
        }
    }
}
