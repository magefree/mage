package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EnlistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BalduvianBerserker extends CardImpl {

    public BalduvianBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Enlist
        this.addAbility(new EnlistAbility());

        // When Balduvian Berserker dies, it deals damage equal to its power to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(
                new SourcePermanentPowerCount()
        ).setText("it deals damage equal to its power to any target"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BalduvianBerserker(final BalduvianBerserker card) {
        super(card);
    }

    @Override
    public BalduvianBerserker copy() {
        return new BalduvianBerserker(this);
    }
}
