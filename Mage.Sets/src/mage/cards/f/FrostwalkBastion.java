package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrostwalkBastion extends CardImpl {

    public FrostwalkBastion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}{S}: Until end of turn, Frostwalk Bastion becomes a 2/3 Construct artifact creature. It's still a land.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(2, 3, "2/3 Construct artifact creature", SubType.CONSTRUCT)
                    .withType(CardType.ARTIFACT),
            CardType.LAND,
            Duration.EndOfTurn
            ).withDurationRuleAtStart(true),
            new ManaCostsImpl<>("{1}{S}")
        ));

        // Whenever Frostwalk Bastion deals combat damage to a creature, tap that creature and it doesn't untap during its controller's next untap step.
        Ability ability = new DealsDamageToACreatureTriggeredAbility(
                new TapTargetEffect("tap that creature"),
                true, false, true
        );
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect()
                .setText("and it doesn't untap during its controller's next untap step")
        );
        this.addAbility(ability);
    }

    private FrostwalkBastion(final FrostwalkBastion card) {
        super(card);
    }

    @Override
    public FrostwalkBastion copy() {
        return new FrostwalkBastion(this);
    }
}
