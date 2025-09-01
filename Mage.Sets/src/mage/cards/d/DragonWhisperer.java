package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.DragonToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DragonWhisperer extends CardImpl {

    public DragonWhisperer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}: Dragon Whisperer gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R}")));

        // {1}{R}: Dragon Whisperer get +1/+0 until end of turn
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")
        ));

        // <Formidable</i> &mdash; {4}{R}{R}: Create a 4/4 red Dragon creature token with flying. Activate this ability only if creatures you control have total power 8 or greater.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new CreateTokenEffect(new DragonToken()), new ManaCostsImpl<>("{4}{R}{R}"), FormidableCondition.instance
        ).setAbilityWord(AbilityWord.FORMIDABLE));
    }

    private DragonWhisperer(final DragonWhisperer card) {
        super(card);
    }

    @Override
    public DragonWhisperer copy() {
        return new DragonWhisperer(this);
    }
}
