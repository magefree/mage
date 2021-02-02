
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class DominusOfFealty extends CardImpl {

    public DominusOfFealty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/R}{U/R}{U/R}{U/R}{U/R}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn), TargetController.YOU, true);
        ability.addEffect(new UntapTargetEffect().setText("If you do, untap it"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("and it gains haste until end of turn"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private DominusOfFealty(final DominusOfFealty card) {
        super(card);
    }

    @Override
    public DominusOfFealty copy() {
        return new DominusOfFealty(this);
    }
}
