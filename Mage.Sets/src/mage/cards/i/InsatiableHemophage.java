package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourceMutatedCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InsatiableHemophage extends CardImpl {

    public InsatiableHemophage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mutate {2}{B}
        this.addAbility(new MutateAbility(this, "{2}{B}"));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever this creature mutates, each opponent loses X life and you gain X life, where X is the number of times this creature has mutated.
        Ability ability = new MutatesSourceTriggeredAbility(
                new LoseLifeOpponentsEffect(SourceMutatedCount.instance).setText("each opponent loses X life")
        );
        ability.addEffect(new GainLifeEffect(SourceMutatedCount.instance)
                .setText("and you gain X life, where X is the number of times this creature has mutated"));
        this.addAbility(ability);
    }

    private InsatiableHemophage(final InsatiableHemophage card) {
        super(card);
    }

    @Override
    public InsatiableHemophage copy() {
        return new InsatiableHemophage(this);
    }
}
