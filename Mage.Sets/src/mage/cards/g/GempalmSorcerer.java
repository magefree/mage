
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class GempalmSorcerer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wizard creatures");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public GempalmSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Cycling {2}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{U}")));

        // When you cycle Gempalm Sorcerer, Wizard creatures gain flying until end of turn.
        Ability ability = new CycleTriggeredAbility(new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filter));
        this.addAbility(ability);
    }

    private GempalmSorcerer(final GempalmSorcerer card) {
        super(card);
    }

    @Override
    public GempalmSorcerer copy() {
        return new GempalmSorcerer(this);
    }
}
