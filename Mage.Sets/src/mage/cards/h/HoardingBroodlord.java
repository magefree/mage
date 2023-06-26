package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HoardingBroodlord extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells you cast from exile");

    static {
        filter.add(new CastFromZonePredicate(Zone.EXILED));
        filter.add(Predicates.not(new AbilityPredicate(ConvokeAbility.class))); // So there are not redundant copies being added to each card
    }

    public HoardingBroodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Hoarding Broodlord enters the battlefield, search your library for a card, exile it face down, then shuffle. For as long as that card remains exiled, you may play it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HoardingBroodlordEffect()));

        // Spells you cast from exile have convoke.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter)));
    }

    private HoardingBroodlord(final HoardingBroodlord card) {
        super(card);
    }

    @Override
    public HoardingBroodlord copy() {
        return new HoardingBroodlord(this);
    }
}

class HoardingBroodlordEffect extends OneShotEffect {

    HoardingBroodlordEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a card, exile it face down, " +
                "then shuffle. For as long as that card remains exiled, you may play it";
    }

    private HoardingBroodlordEffect(final HoardingBroodlordEffect effect) {
        super(effect);
    }

    @Override
    public HoardingBroodlordEffect copy() {
        return new HoardingBroodlordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary();
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        player.shuffleLibrary(source, game);
        if (card != null) {
            player.moveCards(card, Zone.EXILED, source, game);
            card.setFaceDown(true, game);
            CardUtil.makeCardPlayable(game, source, card, Duration.Custom, false);
        }
        return true;
    }
}
