package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Endling extends CardImpl {

    public Endling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {B}: Endling gains menace until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                new MenaceAbility(false), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{B}")));

        // {B}: Endling gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{B}")));

        // {B}: Endling gains undying until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                new UndyingAbility(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{B}")));

        // {1}: Endling gets +1/-1 or -1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new EndlingEffect(), new GenericManaCost(1)));
    }

    private Endling(final Endling card) {
        super(card);
    }

    @Override
    public Endling copy() {
        return new Endling(this);
    }
}

class EndlingEffect extends OneShotEffect {

    EndlingEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} gets +1/-1 or -1/+1 until end of turn";
    }

    private EndlingEffect(final EndlingEffect effect) {
        super(effect);
    }

    @Override
    public EndlingEffect copy() {
        return new EndlingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        int boost = player.chooseUse(outcome, "Give +1/-1 or -1/+1?", null, "+1/-1", "-1/+1", source, game) ? 1 : -1;
        game.addEffect(new BoostSourceEffect(boost, -1 * boost, Duration.EndOfTurn), source);
        return true;
    }
}
