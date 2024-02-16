package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhostOfRamirezDePietro extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with toughness 3 or greater");
    private static final FilterCard filter2
            = new FilterCard("card in a graveyard that was discarded or put there from a library this turn");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 2));
        filter2.add(GhostOfRamirezDePietroPredicate.instance);
    }

    public GhostOfRamirezDePietro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PIRATE);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Ghost of Ramirez DePietro can't be blocked by creatures with toughness 3 or greater.
        this.addAbility(new SimpleEvasionAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)
        ));

        // Whenever Ghost of Ramirez DePietro deals combat damage to a player, choose up to one target card in a graveyard that was discarded or put there from a library this turn. Put that card into its owner's hand.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect()
                        .setText("choose up to one target card in a graveyard that was discarded " +
                                "or put there from a library this turn. Put that card into its owner's hand"),
                false
        );
        ability.addTarget(new TargetCardInGraveyard(filter2));
        this.addAbility(ability, new GhostOfRamirezDePietroWatcher());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private GhostOfRamirezDePietro(final GhostOfRamirezDePietro card) {
        super(card);
    }

    @Override
    public GhostOfRamirezDePietro copy() {
        return new GhostOfRamirezDePietro(this);
    }
}

enum GhostOfRamirezDePietroPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        GhostOfRamirezDePietroWatcher watcher = game.getState().getWatcher(GhostOfRamirezDePietroWatcher.class);
        return watcher != null && watcher.checkCard(input, game);
    }
}

class GhostOfRamirezDePietroWatcher extends Watcher {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    GhostOfRamirezDePietroWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (zEvent.getFromZone() != Zone.LIBRARY
                        || zEvent.getToZone() != Zone.GRAVEYARD) {
                    break;
                }
            case DISCARDED_CARD:
                morSet.add(new MageObjectReference(event.getTargetId(), game));
            default:
                break;
        }
    }

    @Override
    public void reset() {
        super.reset();
        morSet.clear();
    }

    boolean checkCard(Card card, Game game) {
        return morSet.stream().anyMatch(mor -> mor.refersTo(card, game));
    }
}
