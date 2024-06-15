package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Hypnox extends CardImpl {

    public Hypnox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}{B}{B}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Hypnox enters the battlefield, if you cast it from your hand, exile all cards from target opponent's hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new HypnoxExileEffect()),
                CastFromHandSourcePermanentCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it from your hand, exile all cards from target opponent's hand."
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new CastFromHandWatcher());

        // When Hypnox leaves the battlefield, return the exiled cards to their owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.HAND)
                .withText(true, false, false), false));
    }

    private Hypnox(final Hypnox card) {
        super(card);
    }

    @Override
    public Hypnox copy() {
        return new Hypnox(this);
    }
}

class HypnoxExileEffect extends OneShotEffect {

    HypnoxExileEffect() {
        super(Outcome.Exile);
        staticText = "Exile all cards from target opponent's hand";
    }

    private HypnoxExileEffect(final HypnoxExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        return controller.moveCardsToExile(
                player.getHand().getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
        );
    }

    @Override
    public HypnoxExileEffect copy() {
        return new HypnoxExileEffect(this);
    }
}
