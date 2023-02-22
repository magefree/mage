package mage.cards.v;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.condition.common.SourceInGraveyardCondition;
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
import mage.game.permanent.token.PhyrexianBeastToxicToken;

import java.util.UUID;

public class ViralSpawning extends CardImpl {
    public ViralSpawning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        //Create a 3/3 green Phyrexian Beast creature token with toxic 1.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PhyrexianBeastToxicToken(true)));

        //Corrupted — As long as an opponent has three or more poison counters and Viral Spawning is in your graveyard,
        //it has flashback {2}{G}.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{G}")), Duration.EndOfGame, true),
                new CompoundCondition(CorruptedCondition.instance, SourceInGraveyardCondition.instance),
                "as long as an opponent has three or more poison counters and {this} is in your graveyard, it has " +
                        "flashback {2}{G}. <i>(You may cast this card from your graveyard for its flashback cost. Then exile it.)</i>"
        )).setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
    }

    private ViralSpawning(final ViralSpawning card) {
        super(card);
    }

    @Override
    public ViralSpawning copy() {
        return new ViralSpawning(this);
    }
}
