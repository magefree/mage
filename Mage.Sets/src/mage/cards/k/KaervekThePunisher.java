package mage.cards.k;

import mage.ApprovingObject;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KaervekThePunisher extends CardImpl {

    private static final FilterCard filter = new FilterCard("black card from your graveyard");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public KaervekThePunisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you commit a crime, exile up to one target black card from your graveyard and copy it. You may cast the copy. If you do, you lose 2 life.
        Ability ability = new CommittedCrimeTriggeredAbility(new KaervekThePunisherEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private KaervekThePunisher(final KaervekThePunisher card) {
        super(card);
    }

    @Override
    public KaervekThePunisher copy() {
        return new KaervekThePunisher(this);
    }
}

class KaervekThePunisherEffect extends OneShotEffect {

    KaervekThePunisherEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target black card from your graveyard and copy it. "
                + "You may cast the copy. If you do, you lose 2 life";
    }

    private KaervekThePunisherEffect(final KaervekThePunisherEffect effect) {
        super(effect);
    }

    @Override
    public KaervekThePunisherEffect copy() {
        return new KaervekThePunisherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (controller == null || card == null) {
            return false;
        }
        // exile the card
        card.moveToExile(null, "", source, game);
        Card copiedCard = game.copyCard(card, source, source.getControllerId());
        
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
        // You may cast the copy.
        SpellAbility spellAbility = controller.chooseAbilityForCast(copiedCard, game, false);
        if (controller.cast(spellAbility, game, false, new ApprovingObject(source, game))) {
            // If cast, you lose 2 life
            controller.loseLife(2, game, source, false);
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
        return true;
    }

}