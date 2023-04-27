package mage.cards.o;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.FaceDownCastablePredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ObscuringAether extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Face-down creature spells");

    static {
        filter.add(FaceDownCastablePredicate.instance);
    }

    public ObscuringAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Face-down creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // {1}{G}: Turn Obscuring Aether face down.
        Effect effect = new BecomesFaceDownCreatureEffect(Duration.Custom, BecomesFaceDownCreatureEffect.FaceDownType.MANUAL);
        effect.setText("Turn {this} face down. <i>(It becomes a 2/2 creature.)</i>");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{G}")));

    }

    private ObscuringAether(final ObscuringAether card) {
        super(card);
    }

    @Override
    public ObscuringAether copy() {
        return new ObscuringAether(this);
    }
}
