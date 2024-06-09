package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class Brightling extends CardImpl {

    public Brightling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {W}: Brightling gains vigilance until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(
                        VigilanceAbility.getInstance(),
                        Duration.EndOfTurn
                ),
                new ColoredManaCost(ColoredManaSymbol.W)
        ));

        // {W}: Brightling gains lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(
                        LifelinkAbility.getInstance(),
                        Duration.EndOfTurn
                ),
                new ColoredManaCost(ColoredManaSymbol.W)
        ));

        // {W}: Return Brightling to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ReturnToHandSourceEffect(true),
                new ColoredManaCost(ColoredManaSymbol.W)
        ));

        // {1}: Brightling gets +1/-1 or -1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BrightlingEffect(),
                new GenericManaCost(1)
        ));
    }

    private Brightling(final Brightling card) {
        super(card);
    }

    @Override
    public Brightling copy() {
        return new Brightling(this);
    }
}

class BrightlingEffect extends OneShotEffect {

    BrightlingEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} gets +1/-1 or -1/+1 until end of turn";
    }

    private BrightlingEffect(final BrightlingEffect effect) {
        super(effect);
    }

    @Override
    public BrightlingEffect copy() {
        return new BrightlingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        int boost = (player.chooseUse(outcome, "Give +1/-1 or -1/+1?", null, "+1/-1", "-1/+1", source, game) ? 1 : -1);
        game.addEffect(new BoostSourceEffect(boost, -1 * boost, Duration.EndOfTurn), source);
        return true;
    }
}
