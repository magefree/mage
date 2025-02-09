package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LotusguardDisciple extends CardImpl {

    public LotusguardDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, target creature or Vehicle gains lifelink and indestructible until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                .setText("target creature or Vehicle gains lifelink"));
        ability.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_VEHICLE));
        this.addAbility(ability);
    }

    private LotusguardDisciple(final LotusguardDisciple card) {
        super(card);
    }

    @Override
    public LotusguardDisciple copy() {
        return new LotusguardDisciple(this);
    }
}
