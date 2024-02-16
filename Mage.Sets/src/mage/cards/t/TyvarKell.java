package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.command.emblems.TyvarKellEmblem;
import mage.game.permanent.token.ElfWarriorToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class TyvarKell extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "Elves");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.ELF, "Elf");

    public TyvarKell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TYVAR);
        this.setStartingLoyalty(3);

        // Elves you control have "{T}: Add {B}."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new BlackManaAbility(), Duration.WhileOnBattlefield, filter
        )));

        // +1: Put a +1/+1 counter on up to one target Elf. Untap it. It gains deathtouch until end of turn.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 1);
        ability.addEffect(new UntapTargetEffect().setText("Untap it"));
        ability.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn)
                .setText("It gains deathtouch until end of turn")
        );
        ability.addTarget(new TargetPermanent(0, 1, filter2, false));
        this.addAbility(ability);

        // 0: Create a 1/1 green Elf Warrior creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new ElfWarriorToken()), 0));

        // −6: You get an emblem with "Whenever you cast an Elf spell, it gains haste until end of turn and you draw two cards."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TyvarKellEmblem()), -6));
    }

    private TyvarKell(final TyvarKell card) {
        super(card);
    }

    @Override
    public TyvarKell copy() {
        return new TyvarKell(this);
    }
}
