package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TwiningTwins extends AdventureCard {

    public TwiningTwins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{U}{U}", "Swift Spiral", "{1}{W}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new GenericManaCost(1), false));

        // Swift Spiral
        // Exile target nontoken creature. Return it to the battlefield under its owner’s control at the beginning of the next end step.
        this.getSpellCard().getSpellAbility().addEffect(new TwiningTwinsEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_NON_TOKEN));
    }

    private TwiningTwins(final TwiningTwins card) {
        super(card);
    }

    @Override
    public TwiningTwins copy() {
        return new TwiningTwins(this);
    }
}

class TwiningTwinsEffect extends OneShotEffect {

    TwiningTwinsEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target nontoken creature. Return that card to the battlefield under its "
                + "owner's control at the beginning of the next end step";
    }

    private TwiningTwinsEffect(final TwiningTwinsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCardsToExile(permanent, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
        effect.setText("Return the exiled card to the battlefield under its owner's control");
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }

    @Override
    public TwiningTwinsEffect copy() {
        return new TwiningTwinsEffect(this);
    }
}
