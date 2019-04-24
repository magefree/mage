
package mage.cards.s;

import java.util.LinkedList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SistersOfStoneDeath extends CardImpl {

    private UUID exileId = UUID.randomUUID();

    public SistersOfStoneDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);

        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // {G}: Target creature blocks Sisters of Stone Death this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(), new ManaCostsImpl("{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {B}{G}: Exile target creature blocking or blocked by Sisters of Stone Death.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(exileId, this.getIdName()), new ManaCostsImpl("{B}{G}"));
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking or blocked by Sisters of Stone Death");
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()),
                new BlockingAttackerIdPredicate(this.getId())));
        ability2.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability2);

        // {2}{B}: Put a creature card exiled with Sisters of Stone Death onto the battlefield under your control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SistersOfStoneDeathEffect(exileId), new ManaCostsImpl("{2}{B}")));

    }

    public SistersOfStoneDeath(final SistersOfStoneDeath card) {
        super(card);
    }

    @Override
    public SistersOfStoneDeath copy() {
        return new SistersOfStoneDeath(this);
    }
}

class SistersOfStoneDeathEffect extends OneShotEffect {

    private final UUID exileId;

    public SistersOfStoneDeathEffect(UUID exileId) {
        super(Outcome.PutCreatureInPlay);
        this.exileId = exileId;
        staticText = "Put a creature card exiled with {this} onto the battlefield under your control";
    }

    public SistersOfStoneDeathEffect(final SistersOfStoneDeathEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CardsImpl cardsInExile = new CardsImpl();
        TargetCard target = new TargetCard(Zone.EXILED, new FilterCreatureCard());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null) {
                LinkedList<UUID> cards = new LinkedList<>(exile);
                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    cardsInExile.add(card);
                }
                if (controller.choose(Outcome.PutCreatureInPlay, cardsInExile, target, game)) {
                    Card chosenCard = game.getCard(target.getFirstTarget());
                    return controller.moveCards(chosenCard, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        return false;
    }

    @Override
    public SistersOfStoneDeathEffect copy() {
        return new SistersOfStoneDeathEffect(this);
    }
}
