package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetAndTargetControllerEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SynchronizedSpellcraft extends CardImpl {

    public SynchronizedSpellcraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Synchronized Spellcraft deals 4 damage to target creature and X damage to that creature's controller, where X is the number of creatures in your party.
        this.getSpellAbility().addEffect(new SynchronizedSpellcraftEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(PartyCountHint.instance);
    }

    private SynchronizedSpellcraft(final SynchronizedSpellcraft card) {
        super(card);
    }

    @Override
    public SynchronizedSpellcraft copy() {
        return new SynchronizedSpellcraft(this);
    }
}

class SynchronizedSpellcraftEffect extends OneShotEffect {

    SynchronizedSpellcraftEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 4 damage to target creature " +
                "and X damage to that creature's controller, " +
                "where X is the number of creatures in your party. " + PartyCount.getReminder();
    }

    private SynchronizedSpellcraftEffect(final SynchronizedSpellcraftEffect effect) {
        super(effect);
    }

    @Override
    public SynchronizedSpellcraftEffect copy() {
        return new SynchronizedSpellcraftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = PartyCount.instance.calculate(game, source, this);
        return new DamageTargetAndTargetControllerEffect(4, amount).apply(game, source);
    }
}
