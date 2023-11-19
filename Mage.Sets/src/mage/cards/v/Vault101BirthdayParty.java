package mage.cards.v;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.HumanSoldierToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author The_REAL_Boudidi
 */
public final class Vault101BirthdayParty extends CardImpl {

    public Vault101BirthdayParty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create a 1/1 white Human Soldier creature token and a Food token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I,
                new CreateTokenEffect(new HumanSoldierToken()),
                new CreateTokenEffect(new FoodToken()).setText("and a Food token"));

        // II, III -- You may put an Aura or Equipment card from your hand or graveyard onto the battlefield.
        // If an Equipment is put onto the battlefield this way, you may attach it to a creature you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new Vault101BirthdayPartyEffect());

        this.addAbility(sagaAbility);

    }

    private Vault101BirthdayParty(final Vault101BirthdayParty card) {
        super(card);
    }

    @Override
    public Vault101BirthdayParty copy() {
        return new Vault101BirthdayParty(this);
    }

}

class Vault101BirthdayPartyEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("an Aura or Equipment card from your hand or graveyard");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    Vault101BirthdayPartyEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may put an Aura or Equipment card from your hand or graveyard onto the battlefield. "
                         + "If an Equipment is put onto the battlefield this way, you may attach it to a creature you control." ;
    }

    private Vault101BirthdayPartyEffect(final Vault101BirthdayPartyEffect effect) {
        super(effect);
    }

    @Override
    public Vault101BirthdayPartyEffect copy() {
        return new Vault101BirthdayPartyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAllCards(player.getHand().getCards(filter, game));
        cards.addAllCards(player.getGraveyard().getCards(filter, game));
        TargetCard targetCard = new TargetCard(0, 1, Zone.ALL, filter);
        targetCard.withNotTarget(true);
        player.choose(outcome, cards, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent equipment = game.getPermanent(card.getId());
        if (equipment == null || !equipment.hasSubtype(SubType.EQUIPMENT, game)) {
            return false;
        }
        TargetPermanent targetPermanent = new TargetControlledCreaturePermanent(0, 1);
        targetCard.withNotTarget(true);
        player.choose(outcome, targetPermanent, source, game);
        Permanent permanent = game.getPermanent(targetPermanent.getFirstTarget());
        if (permanent != null) {
            permanent.addAttachment(equipment.getId(), source, game);
        }
        return true;
    }
}
