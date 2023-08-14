package mage.cards.n;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author maurer.it_at_gmail.com
 */
public final class NissaRevane extends CardImpl {

    private static final FilterCard nissasChosenFilter = new FilterCard("card named Nissa's Chosen");
    private static final FilterCard elfFilter = new FilterCard("Elf creature cards");

    static {
        nissasChosenFilter.add(new NamePredicate("Nissa's Chosen"));
        elfFilter.add(SubType.ELF.getPredicate());
    }

    public NissaRevane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);
        this.setStartingLoyalty(2);

        LoyaltyAbility ability1 = new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(1, nissasChosenFilter)), 1);
        this.addAbility(ability1);

        LoyaltyAbility ability2 = new LoyaltyAbility(new NissaRevaneGainLifeEffect(), 1);
        this.addAbility(ability2);

        LoyaltyAbility ability3 = new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, Integer.MAX_VALUE, elfFilter)), -7);
        this.addAbility(ability3);
    }

    private NissaRevane(final NissaRevane card) {
        super(card);
    }

    @Override
    public NissaRevane copy() {
        return new NissaRevane(this);
    }
}

class NissaRevaneGainLifeEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ELF);

    public NissaRevaneGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "You gain 2 life for each Elf you control";
    }

    public NissaRevaneGainLifeEffect(final NissaRevaneGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public NissaRevaneGainLifeEffect copy() {
        return new NissaRevaneGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int life = 2 * game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (player != null) {
            player.gainLife(life, game, source);
        }
        return true;
    }

}
