package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KitsuneAce extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent(SubType.VEHICLE, "a Vehicle you control");

    public KitsuneAce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Vehicle you control attacks, choose one —
        // • That vehicle gains first strike until end of turn.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
                new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                        .setText("that vehicle gains first strike until end of turn"),
                false, filter, true
        );

        // • Untap Kitsune Ace.
        ability.addMode(new Mode(new UntapSourceEffect()));
        this.addAbility(ability);
    }

    private KitsuneAce(final KitsuneAce card) {
        super(card);
    }

    @Override
    public KitsuneAce copy() {
        return new KitsuneAce(this);
    }
}
