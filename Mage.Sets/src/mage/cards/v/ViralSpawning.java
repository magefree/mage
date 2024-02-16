package mage.cards.v;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.PhyrexianBeastToxicToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViralSpawning extends CardImpl {

    public ViralSpawning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Create a 3/3 green Phyrexian Beast creature token with toxic 1.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PhyrexianBeastToxicToken()));

        // Corrupted -- As long as an opponent has three or more poison counters and Viral Spawning is in your graveyard, it has flashback {2}{G}.
        this.addAbility(new SimpleStaticAbility(
                Zone.GRAVEYARD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                new FlashbackAbility(this, new ManaCostsImpl<>("{2}{G}")), Duration.Custom, true
                        ), CorruptedCondition.instance, "as long as an opponent has three or more " +
                        "poison counters and {this} is in your graveyard, it has flashback {2}{G}"
                )
        ).setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
    }

    private ViralSpawning(final ViralSpawning card) {
        super(card);
    }

    @Override
    public ViralSpawning copy() {
        return new ViralSpawning(this);
    }
}
