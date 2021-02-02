
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SanctumSeeker extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Vampire you control");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public SanctumSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever a Vampire you control attacks, each opponent loses 1 life and you gain 1 life.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(new LoseLifeOpponentsEffect(1), false, filter);
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private SanctumSeeker(final SanctumSeeker card) {
        super(card);
    }

    @Override
    public SanctumSeeker copy() {
        return new SanctumSeeker(this);
    }
}
