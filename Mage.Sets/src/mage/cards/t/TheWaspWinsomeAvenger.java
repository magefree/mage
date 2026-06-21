package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheWaspWinsomeAvenger extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HERO);
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter2.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public TheWaspWinsomeAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When The Wasp enters, target Hero gains hexproof until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(HexproofAbility.getInstance()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Whenever The Wasp attacks, tap target creature defending player controls.
        Ability ability2 = new AttacksTriggeredAbility(new TapTargetEffect());
        ability2.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability2);
    }

    private TheWaspWinsomeAvenger(final TheWaspWinsomeAvenger card) {
        super(card);
    }

    @Override
    public TheWaspWinsomeAvenger copy() {
        return new TheWaspWinsomeAvenger(this);
    }
}
