package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.keyword.SneakAbility;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ShredderUnrelenting extends CardImpl {

    public ShredderUnrelenting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Sneak {3}{B}
        this.addAbility(new SneakAbility(this, "{3}{B}"));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Shredder enters or attacks, another target creature you control gains deathtouch until end of turn.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GainAbilityTargetEffect(DeathtouchAbility.getInstance()), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private ShredderUnrelenting(final ShredderUnrelenting card) {
        super(card);
    }

    @Override
    public ShredderUnrelenting copy() {
        return new ShredderUnrelenting(this);
    }
}
