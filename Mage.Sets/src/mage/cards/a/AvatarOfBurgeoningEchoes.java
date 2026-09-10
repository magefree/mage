package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class AvatarOfBurgeoningEchoes extends CardImpl {

    public AvatarOfBurgeoningEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Landfall -- Whenever a land you control enters, empower Jace 2.
        this.addAbility(new LandfallAbility(new EmpowerJaceEffect(2)));

        // Planeswalkers you control have "[−10]: Put a +1/+1 counter on target creature for each land you control."
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new AvatarOfBurgeoningEchoesEffect(), -10);
        loyaltyAbility.addTarget(new TargetCreaturePermanent());

        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
            loyaltyAbility,
            Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("Planeswalkers you control have \"-10: Put a +1/+1 counter on target creature for each land you control.\""));
        this.addAbility(ability);
    }

    private AvatarOfBurgeoningEchoes(final AvatarOfBurgeoningEchoes card) {
        super(card);
    }

    @Override
    public AvatarOfBurgeoningEchoes copy() {
        return new AvatarOfBurgeoningEchoes(this);
    }
}

class AvatarOfBurgeoningEchoesEffect extends OneShotEffect {

    static final FilterControlledPermanent filter = new FilterControlledPermanent("land");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public AvatarOfBurgeoningEchoesEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put a +1/+1 counter on target creature for each land you control";
    }

    private AvatarOfBurgeoningEchoesEffect(final AvatarOfBurgeoningEchoesEffect effect) {
        super(effect);
    }

    @Override
    public AvatarOfBurgeoningEchoesEffect copy() {
        return new AvatarOfBurgeoningEchoesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
            if (count > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(count), source.getControllerId(), source, game);
                return true;
            }
        }
        return false;
    }
}
