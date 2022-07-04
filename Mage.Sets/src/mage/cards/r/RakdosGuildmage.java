
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.RakdosGuildmageGoblinToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class RakdosGuildmage extends CardImpl {

    public RakdosGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/R}{B/R}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>({BR} can be paid with either {B} or {R}.)</i>
        // {3}{B}, Discard a card: Target creature gets -2/-2 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // {3}{R}: Create a 2/1 red Goblin creature token with haste. Exile it at the beginning of the next end step.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RakdosGuildmageEffect(), new ManaCostsImpl<>("{3}{R}"));
        this.addAbility(ability2);
    }

    private RakdosGuildmage(final RakdosGuildmage card) {
        super(card);
    }

    @Override
    public RakdosGuildmage copy() {
        return new RakdosGuildmage(this);
    }
}

class RakdosGuildmageEffect extends OneShotEffect {

    public RakdosGuildmageEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 2/1 red Goblin creature token with haste. Exile it at the beginning of the next end step";
    }

    public RakdosGuildmageEffect(final RakdosGuildmageEffect effect) {
        super(effect);
    }

    @Override
    public RakdosGuildmageEffect copy() {
        return new RakdosGuildmageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new RakdosGuildmageGoblinToken());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }
}
