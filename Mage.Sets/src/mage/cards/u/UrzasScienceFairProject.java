
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
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
        this.staticText = "Roll a six-sided die. {this} gets the indicated result. 1 - -2/-2 until end of turn. 2 - Prevent all combat damage it would deal this turn. 3 - gains vigilance until end of turn. 4 - gains first strike until end of turn. 5 - gains flying until end of turn. 6 - gets +2/+2 until end of turn";
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
        if (controller != null) {
            int amount = controller.rollDice(outcome, source, game, 6);

            Effect effect = null;
            // 1 - -2/-2 until end of turn.
            // 2 - Prevent all combat damage it would deal this turn.
            // 3 - gains vigilance until end of turn.
            // 4 - gains first strike until end of turn.
            // 5 - gains flying until end of turn.
            // 6 - gets +2/+2 until end of turn";
            if (amount == 1) {
                game.addEffect(new BoostSourceEffect(-2, -2, Duration.EndOfTurn), source);
            } else if (amount == 2) {
                game.addEffect(new PreventCombatDamageBySourceEffect(Duration.EndOfTurn), source);
            } else if (amount == 3) {
                game.addEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn), source);
            } else if (amount == 4) {
                game.addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), source);
            } else if (amount == 5) {
                game.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), source);
            } else if (amount == 6) {
                game.addEffect(new BoostSourceEffect(+2, +2, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }
}
