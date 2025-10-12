package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DonnieAndAprilAdorkableDuo extends CardImpl {

    private static final FilterPlayer filter0 = new FilterPlayer("a different player");
    private static final FilterPlayer filter1 = new FilterPlayer();
    private static final FilterPlayer filter2 = new FilterPlayer();

    static {
        filter1.add(new AnotherTargetPredicate(1, true));
        filter2.add(new AnotherTargetPredicate(2, true));
    }

    public DonnieAndAprilAdorkableDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Donnie & April enter, choose one or both. Each mode must target a different player.
        // * Target player draws two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardTargetEffect(2));
        ability.addTarget(new TargetPlayer(filter1).withChooseHint("to draw a card"));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(2);
        ability.getModes().setLimitUsageByOnce(false);
        ability.getModes().setMaxModesFilter(filter0);

        // * Target player returns an artifact, instant, or sorcery card from their graveyard to their hand.
        ability.addMode(new Mode(new DonnieAndAprilAdorkableDuoEffect())
                .addTarget(new TargetPlayer(filter2).withChooseHint("to return a card from their graveyard to their hand")));
        this.addAbility(ability);
    }

    private DonnieAndAprilAdorkableDuo(final DonnieAndAprilAdorkableDuo card) {
        super(card);
    }

    @Override
    public DonnieAndAprilAdorkableDuo copy() {
        return new DonnieAndAprilAdorkableDuo(this);
    }
}

class DonnieAndAprilAdorkableDuoEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("artifact, instant, or sorcery card");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    DonnieAndAprilAdorkableDuoEffect() {
        super(Outcome.Benefit);
        staticText = "target player returns an artifact, instant, or sorcery card from their graveyard to their hand";
    }

    private DonnieAndAprilAdorkableDuoEffect(final DonnieAndAprilAdorkableDuoEffect effect) {
        super(effect);
    }

    @Override
    public DonnieAndAprilAdorkableDuoEffect copy() {
        return new DonnieAndAprilAdorkableDuoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || player.getGraveyard().count(filter, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        player.choose(Outcome.ReturnToHand, player.getGraveyard(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
