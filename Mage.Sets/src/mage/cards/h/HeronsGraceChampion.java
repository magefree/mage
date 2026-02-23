package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HeronsGraceChampion extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN);

    public HeronsGraceChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Heron's Grace Champion enters the battlefield, other Humans you control get +1/+1 and gain lifelink until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn, filter, true
        ).setText("other Humans you control get +1/+1"));
        ability.addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn, filter, true
        ).setText("and gain lifelink until end of turn"));
        this.addAbility(ability);
    }

    private HeronsGraceChampion(final HeronsGraceChampion card) {
        super(card);
    }

    @Override
    public HeronsGraceChampion copy() {
        return new HeronsGraceChampion(this);
    }
}
