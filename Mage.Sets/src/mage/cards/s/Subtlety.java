package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Subtlety extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature spell or planeswalker spell");
    private static final FilterCard filter2 = new FilterCard("a blue card from your hand");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
        filter2.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public Subtlety(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Subtlety enters the battlefield, choose up to one target creature spell or planeswalker spell. Its owner puts it on the top or bottom of their library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SubtletyEffect());
        ability.addTarget(new TargetSpell(0, 1, filter));
        this.addAbility(ability);

        // Evoke—Exile a blue card from your hand.
        this.addAbility(new EvokeAbility(new ExileFromHandCost(new TargetCardInHand(filter2))));
    }

    private Subtlety(final Subtlety card) {
        super(card);
    }

    @Override
    public Subtlety copy() {
        return new Subtlety(this);
    }
}

class SubtletyEffect extends OneShotEffect {

    SubtletyEffect() {
        super(Outcome.Removal);
        staticText = "choose up to one target creature spell or planeswalker spell. " +
                "Its owner puts it on the top or bottom of their library";
    }

    private SubtletyEffect(final SubtletyEffect effect) {
        super(effect);
    }

    @Override
    public SubtletyEffect copy() {
        return new SubtletyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        if (player.chooseUse(Outcome.Detriment, "Put the targeted spell on the top or bottom of your library?",
                "", "Top", "Bottom", source, game)) {
            return new PutOnLibraryTargetEffect(true).apply(game, source);
        }
        return new PutOnLibraryTargetEffect(false).apply(game, source);
    }
}
