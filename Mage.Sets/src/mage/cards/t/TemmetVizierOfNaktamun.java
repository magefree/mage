
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class TemmetVizierOfNaktamun extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public TemmetVizierOfNaktamun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, target creature token you control gets +1/+1 until end of turn and can't be blocked this turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), TargetController.YOU, false);
        Effect effect = new CantBeBlockedTargetEffect();
        effect.setText(" and can't be blocked this turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);

        // Embalm {3}{W}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{3}{W}{U}"), this));
    }

    private TemmetVizierOfNaktamun(final TemmetVizierOfNaktamun card) {
        super(card);
    }

    @Override
    public TemmetVizierOfNaktamun copy() {
        return new TemmetVizierOfNaktamun(this);
    }
}
