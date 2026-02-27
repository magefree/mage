
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author klayhamn
 */
public final class ForiysianTotem extends CardImpl {

    private static final String ruleText = "As long as {this} is a creature, it can block an additional creature each combat.";

    public ForiysianTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {4}{R}: Foriysian Totem becomes a 4/4 red Giant artifact creature with trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(4, 4, "4/4 red Giant artifact creature with trample", SubType.GIANT)
                    .withColor("R").withType(CardType.ARTIFACT).withAbility(TrampleAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            new ManaCostsImpl<>("{4}{R}")
        ));

        // As long as Foriysian Totem is a creature, it can block an additional creature each combat.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(new CanBlockAdditionalCreatureEffect(1), new SourceMatchesFilterCondition(new FilterCreaturePermanent()), ruleText)));
    }

    private ForiysianTotem(final ForiysianTotem card) {
        super(card);
    }

    @Override
    public ForiysianTotem copy() {
        return new ForiysianTotem(this);
    }

}
