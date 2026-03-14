package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RunoStromkirk extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterAttackingCreature("another attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public RunoStromkirk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.CLERIC}, "{1}{U}{B}",
                "Krothuss, Lord of the Deep",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.KRAKEN, SubType.HORROR}, "UB"
        );

        // Runo Stromkirk
        this.getLeftHalfCard().setPT(1, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When Runo Stromkirk enters the battlefield, put up to one target creature card from your graveyard on top of your library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If a creature card with mana value 6 or greater is revealed this way, transform Runo Stromkirk.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new RunoStromkirkEffect()));

        // Krothuss, Lord of the Deep
        this.getRightHalfCard().setPT(3, 5);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Krothuss, Lord of the Deep attacks, create a tapped and attacking token that's a copy of another target attacking creature. If that creature is a Kraken, Leviathan, Octopus, or Serpent, create two of those tokens instead.
        Ability ability2 = new AttacksTriggeredAbility(new KrothussLordOfTheDeepEffect());
        ability2.addTarget(new TargetPermanent(filter));
        this.getRightHalfCard().addAbility(ability2);
    }

    private RunoStromkirk(final RunoStromkirk card) {
        super(card);
    }

    @Override
    public RunoStromkirk copy() {
        return new RunoStromkirk(this);
    }
}

class RunoStromkirkEffect extends OneShotEffect {

    RunoStromkirkEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. You may reveal that card. " +
                "If a creature card with mana value 6 or greater is revealed this way, transform {this}";
    }

    private RunoStromkirkEffect(final RunoStromkirkEffect effect) {
        super(effect);
    }

    @Override
    public RunoStromkirkEffect copy() {
        return new RunoStromkirkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards(null, card, game);
        if (!player.chooseUse(outcome, "Reveal " + card.getName() + '?', source, game)) {
            return true;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (!card.isCreature(game) || card.getManaValue() < 6) {
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.transform(source, game);
        }
        return true;
    }
}


class KrothussLordOfTheDeepEffect extends OneShotEffect {

    KrothussLordOfTheDeepEffect() {
        super(Outcome.Benefit);
        staticText = "create a tapped and attacking token that's a copy of another target attacking creature. " +
                "If that creature is a Kraken, Leviathan, Octopus, or Serpent, create two of those tokens instead";
    }

    private KrothussLordOfTheDeepEffect(final KrothussLordOfTheDeepEffect effect) {
        super(effect);
    }

    @Override
    public KrothussLordOfTheDeepEffect copy() {
        return new KrothussLordOfTheDeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int count = permanent.hasSubtype(SubType.KRAKEN, game)
                || permanent.hasSubtype(SubType.LEVIATHAN, game)
                || permanent.hasSubtype(SubType.OCTOPUS, game)
                || permanent.hasSubtype(SubType.SERPENT, game) ? 2 : 1;
        return new CreateTokenCopyTargetEffect(
                null, null,
                false, count, true, true
        ).apply(game, source);
    }
}
