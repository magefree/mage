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
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class TeyoDiamondbladeMage extends CardImpl {

    public TeyoDiamondbladeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Teyo enters, target permanent you control gains deathtouch until end of turn. Put a +1/+1 counter on it if it's a creature. Put a loyalty counter on it if it's a planeswalker.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn));
        ability.addEffect(new TeyoDiamondbladeMageEffect());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private TeyoDiamondbladeMage(final TeyoDiamondbladeMage card) {
        super(card);
    }

    @Override
    public TeyoDiamondbladeMage copy() {
        return new TeyoDiamondbladeMage(this);
    }
}

class TeyoDiamondbladeMageEffect extends OneShotEffect {

    public TeyoDiamondbladeMageEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put a +1/+1 counter on it if it's a creature. Put a loyalty counter on it if it's a planeswalker.";
    }

    private TeyoDiamondbladeMageEffect(final TeyoDiamondbladeMageEffect effect) {
        super(effect);
    }

    @Override
    public TeyoDiamondbladeMageEffect copy() {
        return new TeyoDiamondbladeMageEffect(this);
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