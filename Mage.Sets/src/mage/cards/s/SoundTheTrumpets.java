package mage.cards.s;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.RecruitEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.TargetSpell;

/**
 * @author muz
 */
public final class SoundTheTrumpets extends CardImpl {

    public SoundTheTrumpets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell. If that spell's mana value was 2 or less, recruit.
        this.getSpellAbility().addEffect(new SoundTheTrumpetsEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private SoundTheTrumpets(final SoundTheTrumpets card) {
        super(card);
    }

    @Override
    public SoundTheTrumpets copy() {
        return new SoundTheTrumpets(this);
    }
}

class SoundTheTrumpetsEffect extends OneShotEffect {

    SoundTheTrumpetsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. If that spell's mana value was 2 or less, recruit";
    }

    private SoundTheTrumpetsEffect(final SoundTheTrumpetsEffect effect) {
        super(effect);
    }

    @Override
    public SoundTheTrumpetsEffect copy() {
        return new SoundTheTrumpetsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getFirstTarget());
        if (object == null) {
            return false;
        }
        new CounterTargetEffect().apply(game, source);
        int manaValue = object.getManaValue();
        if (manaValue <= 2) {
            new RecruitEffect().apply(game, source);
        }
        return true;
    }
}
