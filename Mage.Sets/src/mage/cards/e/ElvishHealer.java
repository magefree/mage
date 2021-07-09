package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElvishHealer extends CardImpl {

    public ElvishHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Prevent the next 1 damage that would be dealt to any target this turn. If it’s a green creature, prevent the next 2 damage instead.
        Ability ability = new SimpleActivatedAbility(new ElvishHealerEffect(), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ElvishHealer(final ElvishHealer card) {
        super(card);
    }

    @Override
    public ElvishHealer copy() {
        return new ElvishHealer(this);
    }
}

class ElvishHealerEffect extends OneShotEffect {

    ElvishHealerEffect() {
        super(Outcome.Benefit);
        staticText = "Prevent the next 1 damage that would be dealt to any target this turn. " +
                "If it's a green creature, prevent the next 2 damage instead.";
    }

    private ElvishHealerEffect(final ElvishHealerEffect effect) {
        super(effect);
    }

    @Override
    public ElvishHealerEffect copy() {
        return new ElvishHealerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int toPrevent = 1;
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null && permanent.isCreature(game) && permanent.getColor(game).isGreen()) {
            toPrevent = 2;
        }
        game.addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, toPrevent)
                .setTargetPointer(new FixedTarget(source.getFirstTarget(), game)), source);
        return true;
    }
}