package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class NyleasColossus extends CardImpl {

    public NyleasColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{6}{G}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Constellation — Whenever Nylea's Colossus or another enchantment enters the battlefield under your control, double target creature's power and toughness until end of turn.
        Ability ability = new ConstellationAbility(new NyleasColossusEffect(), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NyleasColossus(final NyleasColossus card) {
        super(card);
    }

    @Override
    public NyleasColossus copy() {
        return new NyleasColossus(this);
    }
}

class NyleasColossusEffect extends OneShotEffect {

    public NyleasColossusEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "double target creature's power and toughness until end of turn";
    }

    private NyleasColossusEffect(final NyleasColossusEffect effect) {
        super(effect);
    }

    @Override
    public NyleasColossusEffect copy() {
        return new NyleasColossusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        int toughness = permanent.getToughness().getValue();
        game.addEffect(new BoostTargetEffect(power, toughness, Duration.EndOfTurn), source);
        return true;
    }
}
