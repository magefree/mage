package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class TeyoLightshieldExpert extends CardImpl {

    public TeyoLightshieldExpert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Teyo enters, target permanent you control gains hexproof until end of turn. Put a +1/+1 counter on it if it's a creature. Put a loyalty counter on it if it's a planeswalker.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn));
        ability.addEffect(new TeyoLightshieldExpertEffect());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private TeyoLightshieldExpert(final TeyoLightshieldExpert card) {
        super(card);
    }

    @Override
    public TeyoLightshieldExpert copy() {
        return new TeyoLightshieldExpert(this);
    }
}

class TeyoLightshieldExpertEffect extends OneShotEffect {

    public TeyoLightshieldExpertEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put a +1/+1 counter on it if it's a creature. Put a loyalty counter on it if it's a planeswalker.";
    }

    private TeyoLightshieldExpertEffect(final TeyoLightshieldExpertEffect effect) {
        super(effect);
    }

    @Override
    public TeyoLightshieldExpertEffect copy() {
        return new TeyoLightshieldExpertEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            if (permanent.isCreature()) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            } else if (permanent.isPlaneswalker()) {
                permanent.addCounters(CounterType.LOYALTY.createInstance(), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}