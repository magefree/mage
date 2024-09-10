package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TranquilFrillback extends CardImpl {

    public TranquilFrillback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Tranquil Frillback enters the battlefield, you may pay {G} up to three times. When you pay this cost one or more times, choose up to that many--
        // * Destroy target artifact or enchantment.
        // * Exile target player's graveyard.
        // * You gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TranquilFrillbackEffect()));
    }

    private TranquilFrillback(final TranquilFrillback card) {
        super(card);
    }

    @Override
    public TranquilFrillback copy() {
        return new TranquilFrillback(this);
    }
}

class TranquilFrillbackEffect extends OneShotEffect {

    private static ReflexiveTriggeredAbility makeAbility() {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        ability.addMode(new Mode(new ExileGraveyardAllTargetPlayerEffect()).addTarget(new TargetPlayer()));
        ability.addMode(new Mode(new GainLifeEffect(4)));
        ability.getModes().setMinModes(0);
        ability.getModes().setChooseText("choose up to that many &mdash;");
        return ability;
    }

    TranquilFrillbackEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {G} up to three times. " +
                "When you pay this cost one or more times, " + makeAbility().getRule();
    }

    private TranquilFrillbackEffect(final TranquilFrillbackEffect effect) {
        super(effect);
    }

    @Override
    public TranquilFrillbackEffect copy() {
        return new TranquilFrillbackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < 3; i++) {
            Cost cost = new ManaCostsImpl<>("{G}");
            if (!cost.canPay(source, source, source.getControllerId(), game)
                    || !cost.pay(source, game, source, source.getControllerId(), false)) {
                break;
            }
            count++;
        }
        if (count < 1) {
            return false;
        }
        ReflexiveTriggeredAbility ability = makeAbility();
        ability.getModes().setMaxModes(count);
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
