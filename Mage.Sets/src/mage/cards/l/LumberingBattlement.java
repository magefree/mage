package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class LumberingBattlement extends CardImpl {

    public LumberingBattlement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Lumbering Battlement enters the battlefield, exile any number of other nontoken creatures you control until it leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LumberingBattlementEffect());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

        // Lumbering Battlement gets +2/+2 for each card exiled with it.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                LumberingBattlementValue.instance,
                LumberingBattlementValue.instance,
                Duration.WhileOnBattlefield
        ).setText("{this} gets +2/+2 for each card exiled with it.")));
    }

    private LumberingBattlement(final LumberingBattlement card) {
        super(card);
    }

    @Override
    public LumberingBattlement copy() {
        return new LumberingBattlement(this);
    }
}

class LumberingBattlementEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("other nontoken creatures");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
    }

    LumberingBattlementEffect() {
        super(Benefit);
        staticText = "exile any number of other nontoken creatures you control until it leaves the battlefield";
    }

    private LumberingBattlementEffect(final LumberingBattlementEffect effect) {
        super(effect);
    }

    @Override
    public LumberingBattlementEffect copy() {
        return new LumberingBattlementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePerm = source.getSourcePermanentIfItStillExists(game);
        if (player == null || sourcePerm == null) {
            return false;
        }
        Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        if (!player.choose(Outcome.Neutral, target, source, game)) {
            return false;
        }
        Set<Card> cards = new HashSet<>();
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                cards.add(permanent);
            }
        }
        return player.moveCardsToExile(
                cards, source, game, true,
                CardUtil.getCardExileZoneId(game, source), sourcePerm.getIdName()
        );
    }
}

enum LumberingBattlementValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility == null) {
            return 0;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(
                game, sourceAbility.getSourceId(),
                sourceAbility.getSourceObjectZoneChangeCounter()
        ));
        if (exileZone == null) {
            exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, sourceAbility));
        }
        if (exileZone == null) {
            return 0;
        }
        int counter = 0;
        for (UUID cardId : exileZone) {
            Card card = game.getCard(cardId);
            if (card != null) {
                counter++;
            }
        }
        return 2 * counter;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}