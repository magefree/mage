package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MentorAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class AureliaExemplarOfJustice extends CardImpl {

    public AureliaExemplarOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Mentor
        this.addAbility(new MentorAbility());

        // At the beginning of combat on your turn, choose up to one target creature you control. Until end of turn, that creature gets +2/+0, gains trample if it's red, and gains vigilance if it's white.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AureliaExemplarOfJusticeEffect(),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private AureliaExemplarOfJustice(final AureliaExemplarOfJustice card) {
        super(card);
    }

    @Override
    public AureliaExemplarOfJustice copy() {
        return new AureliaExemplarOfJustice(this);
    }
}

class AureliaExemplarOfJusticeEffect extends OneShotEffect {

    public AureliaExemplarOfJusticeEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose up to one target creature you control. "
                + "Until end of turn, that creature gets +2/+0, "
                + "gains trample if it's red, "
                + "and gains vigilance if it's white.";
    }

    public AureliaExemplarOfJusticeEffect(final AureliaExemplarOfJusticeEffect effect) {
        super(effect);
    }

    @Override
    public AureliaExemplarOfJusticeEffect copy() {
        return new AureliaExemplarOfJusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn), source);
        if (creature.getColor(game).isRed()) {
            game.addEffect(new GainAbilityTargetEffect(
                    TrampleAbility.getInstance(), Duration.EndOfTurn
            ), source);
        }
        if (creature.getColor(game).isWhite()) {
            game.addEffect(new GainAbilityTargetEffect(
                    VigilanceAbility.getInstance(), Duration.EndOfTurn
            ), source);
        }
        return true;
    }
}
