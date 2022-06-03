package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaptainNghathrod extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.HORROR, "a Horror");
    private static final FilterCard filter2 = new FilterCard(
            "artifact or creature card in an opponent's graveyard that was put there from their library this turn"
    );

    static {
        filter2.add(CaptainNghathrodWatcher::checkCard);
    }

    public CaptainNghathrod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Horrors you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter
        ).setText("Horrors you control have menace")));

        // Whenever a Horror you control deals combat damage to a player, that player mills that many cards.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new MillCardsTargetEffect(SavedDamageValue.MANY).setText("that player mills that many cards"),
                filter, false, SetTargetPointer.PLAYER, true, true
        ));

        // At the beginning of your end step, choose target artifact or creature card in an opponent's graveyard that was put there from their library this turn. Put it onto the battlefield under your control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("choose target artifact or creature card in an opponent's graveyard that was put " +
                                "there from their library this turn. Put it onto the battlefield under your control"),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter2));
        this.addAbility(ability, new CaptainNghathrodWatcher());
    }

    private CaptainNghathrod(final CaptainNghathrod card) {
        super(card);
    }

    @Override
    public CaptainNghathrod copy() {
        return new CaptainNghathrod(this);
    }
}

class CaptainNghathrodWatcher extends Watcher {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    CaptainNghathrodWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.LIBRARY && zEvent.getToZone() == Zone.GRAVEYARD) {
            morSet.add(new MageObjectReference(zEvent.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        morSet.clear();
        super.reset();
    }

    static boolean checkCard(Card card, Game game) {
        return (card.isCreature(game) || card.isArtifact(game))
                && game
                .getState()
                .getWatcher(CaptainNghathrodWatcher.class)
                .morSet
                .stream()
                .anyMatch(mor -> mor.refersTo(card, game));
    }
}
