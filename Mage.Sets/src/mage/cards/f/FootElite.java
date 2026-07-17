package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FootElite extends CardImpl {

    public FootElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W/B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever this creature attacks, another target creature you control gets +1/+0 and gains indestructible until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(1, 0).setText("another target creature you control gets +1/+0"));
        ability.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()).setText("and gains indestructible until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private FootElite(final FootElite card) {
        super(card);
    }

    @Override
    public FootElite copy() {
        return new FootElite(this);
    }
}
