
package mage.cards.t;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class TezzeretTheSeeker extends CardImpl {

    public TezzeretTheSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEZZERET);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Untap up to two target artifacts.
        LoyaltyAbility ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addTarget(new TargetArtifactPermanent(0, 2));
        this.addAbility(ability);
        // -X: Search your library for an artifact card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new LoyaltyAbility(new TezzeretTheSeekerEffect2()));
        // -5: Artifacts you control become artifact creatures with base power and toughness 5/5 until end of turn.
        this.addAbility(new LoyaltyAbility(new TezzeretTheSeekerEffect3(), -5));
    }

    public TezzeretTheSeeker(final TezzeretTheSeeker card) {
        super(card);
    }

    @Override
    public TezzeretTheSeeker copy() {
        return new TezzeretTheSeeker(this);
    }
}

class TezzeretTheSeekerEffect2 extends OneShotEffect {

    public TezzeretTheSeekerEffect2() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for an artifact card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library";
    }

    public TezzeretTheSeekerEffect2(final TezzeretTheSeekerEffect2 effect) {
        super(effect);
    }

    @Override
    public TezzeretTheSeekerEffect2 copy() {
        return new TezzeretTheSeekerEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int cmc = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                cmc = ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }

        FilterArtifactCard filter = new FilterArtifactCard("artifact card with converted mana cost " + cmc + " or less");
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, cmc + 1));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);

        if (controller.searchLibrary(target, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }
}

class TezzeretTheSeekerEffect3 extends ContinuousEffectImpl {

    public TezzeretTheSeekerEffect3() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        this.staticText = "Artifacts you control become artifact creatures with base power and toughness 5/5 until end of turn";
    }

    public TezzeretTheSeekerEffect3(final TezzeretTheSeekerEffect3 effect) {
        super(effect);
    }

    @Override
    public TezzeretTheSeekerEffect3 copy() {
        return new TezzeretTheSeekerEffect3(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            if (!permanent.isArtifact()) {
                                permanent.addCardType(CardType.ARTIFACT);
                            }
                            if (!permanent.isCreature()) {
                                permanent.addCardType(CardType.CREATURE);
                            }
                            permanent.getSubtype(game).clear();
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setValue(5);
                            permanent.getToughness().setValue(5);
                        }
                }
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.PTChangingEffects_7;
    }
}
