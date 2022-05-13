
package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class UrzasScienceFairProject extends CardImpl {

    public UrzasScienceFairProject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //  {2}: Roll a six-sided die. Urza's Science Fair Project gets the indicated result. 1 - It gets -2/-2 until end of turn. 2 - Prevent all combat damage it would deal this turn. 3 - It gains vigilance until end of turn. 4 - It gains first strike until end of turn. 5 - It gains flying until end of turn. 6 - It gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UrzasScienceFairProjectEffect(), new GenericManaCost(2));
        this.addAbility(ability);
    }

    private UrzasScienceFairProject(final UrzasScienceFairProject card) {
        super(card);
    }

    @Override
    public UrzasScienceFairProject copy() {
        return new UrzasScienceFairProject(this);
    }
}

class UrzasScienceFairProjectEffect extends OneShotEffect {

    public UrzasScienceFairProjectEffect() {
        super(Outcome.Benefit);
        this.staticText = "Roll a six-sided die. {this} gets the indicated result." +
                "<br>1 - It gets -2/-2 until end of turn." +
                "<br>2 - Prevent all combat damage it would deal this turn." +
                "<br>3 - It gains vigilance until end of turn." +
                "<br>4 - It gains first strike until end of turn." +
                "<br>5 - It gains flying until end of turn." +
                "<br>6 - It gets +2/+2 until end of turn";
    }

    public UrzasScienceFairProjectEffect(final UrzasScienceFairProjectEffect effect) {
        super(effect);
    }

    @Override
    public UrzasScienceFairProjectEffect copy() {
        return new UrzasScienceFairProjectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int amount = controller.rollDice(outcome, source, game, 6);

        ContinuousEffect effect;
        // 1 - -2/-2 until end of turn.
        // 2 - Prevent all combat damage it would deal this turn.
        // 3 - gains vigilance until end of turn.
        // 4 - gains first strike until end of turn.
        // 5 - gains flying until end of turn.
        // 6 - gets +2/+2 until end of turn";
        switch (amount) {
            case 1:
                effect = new BoostSourceEffect(-2, -2, Duration.EndOfTurn);
                break;
            case 2:
                effect = new PreventCombatDamageBySourceEffect(Duration.EndOfTurn);
                break;
            case 3:
                effect = new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn);
                break;
            case 4:
                effect = new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
                break;
            case 5:
                effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
                break;
            case 6:
                effect = new BoostSourceEffect(+2, +2, Duration.EndOfTurn);
                break;
            default:
                return true;
        }
        game.addEffect(effect, source);
        return true;
    }
}
