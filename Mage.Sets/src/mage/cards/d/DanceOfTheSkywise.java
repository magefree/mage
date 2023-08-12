
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class DanceOfTheSkywise extends CardImpl {

    public DanceOfTheSkywise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Until end of turn, target creature you control becomes a blue Dragon Illusion with base power and toughness 4/4, loses all abilities, and gains flying.
        Effect effect = new BecomesCreatureTargetEffect(
                new DragonIllusionToken(), true, false, Duration.EndOfTurn)
                .withDurationRuleAtStart(true);
        effect.setText("Until end of turn, target creature you control becomes a blue Dragon Illusion with base power and toughness 4/4, loses all abilities, and gains flying.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private DanceOfTheSkywise(final DanceOfTheSkywise card) {
        super(card);
    }

    @Override
    public DanceOfTheSkywise copy() {
        return new DanceOfTheSkywise(this);
    }
    
        private static class DragonIllusionToken extends TokenImpl {

        public DragonIllusionToken() {
            super("Dragon", "blue Dragon Illusion with base power and toughness 4/4 and with flying");
            cardType.add(CardType.CREATURE);
            color.setBlue(true);
            subtype.add(SubType.DRAGON);
            subtype.add(SubType.ILLUSION);            
            power = new MageInt(4);
            toughness = new MageInt(4);
            this.addAbility(FlyingAbility.getInstance());
        }
        public DragonIllusionToken(final DragonIllusionToken token) {
            super(token);
        }

        public DragonIllusionToken copy() {
            return new DragonIllusionToken(this);
        }

    }
}
