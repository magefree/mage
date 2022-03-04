package mage.cards.d;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadlyDancer extends CardImpl {

    public DeadlyDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setRed(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature transforms into Deadly Dancer, add {R}{R}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new TransformIntoSourceTriggeredAbility(new DeadlyDancerEffect()));

        // {R}{R}: Deadly Dancer and another target creature each get +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ).setText("{this}"), new ManaCostsImpl<>("{R}{R}"));
        ability.addEffect(new BoostTargetEffect(1, 0)
                .setText("and another target creature each get +1/+0 until end of turn"));
        this.addAbility(ability);
    }

    private DeadlyDancer(final DeadlyDancer card) {
        super(card);
    }

    @Override
    public DeadlyDancer copy() {
        return new DeadlyDancer(this);
    }
}

class DeadlyDancerEffect extends OneShotEffect {

    DeadlyDancerEffect() {
        super(Outcome.Benefit);
        staticText = "add {R}{R}. Until end of turn, you don't lose this mana as steps and phases end";
    }

    private DeadlyDancerEffect(final DeadlyDancerEffect effect) {
        super(effect);
    }

    @Override
    public DeadlyDancerEffect copy() {
        return new DeadlyDancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.getManaPool().addMana(Mana.RedMana(2), game, source, true);
        return true;
    }
}
