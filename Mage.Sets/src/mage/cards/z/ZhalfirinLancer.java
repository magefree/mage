package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZhalfirinLancer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.KNIGHT, "another Knight");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ZhalfirinLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another Knight enters the battlefield under your control, Zhalfirin Lancer gets +1/+1 and gains vigilance until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn)
                        .setText("{this} gets +1/+1"), filter
        );
        ability.addEffect(new GainAbilitySourceEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains vigilance until end of turn"));
        this.addAbility(ability);
    }

    private ZhalfirinLancer(final ZhalfirinLancer card) {
        super(card);
    }

    @Override
    public ZhalfirinLancer copy() {
        return new ZhalfirinLancer(this);
    }
}
