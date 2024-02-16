package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class DanceOfTheManse extends CardImpl {

    public DanceOfTheManse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{U}");

        // Return up to X target artifact and/or non-Aura enchantment cards each with converted mana cost X or less from your graveyard to the battlefield. If X is 6 or more, those permanents are 4/4 creatures in addition to their other types.
        this.getSpellAbility().addEffect(new DanceOfTheManseEffect());
        this.getSpellAbility().setTargetAdjuster(DanceOfTheManseAdjuster.instance);
    }

    private DanceOfTheManse(final DanceOfTheManse card) {
        super(card);
    }

    @Override
    public DanceOfTheManse copy() {
        return new DanceOfTheManse(this);
    }
}

enum DanceOfTheManseAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filter = new FilterCard("artifact and/or non-Aura enchantment cards " +
                "each with mana value " + xValue + " or less from your graveyard");
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                Predicates.and(
                        CardType.ENCHANTMENT.getPredicate(),
                        Predicates.not(SubType.AURA.getPredicate())
                )
        ));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(0, xValue, filter));
    }
}

class DanceOfTheManseEffect extends OneShotEffect {

    DanceOfTheManseEffect() {
        super(Outcome.Benefit);
        staticText = "Return up to X target artifact and/or non-Aura enchantment cards " +
                "each with mana value X or less from your graveyard to the battlefield. " +
                "If X is 6 or more, those permanents are 4/4 creatures in addition to their other types.";
    }

    private DanceOfTheManseEffect(final DanceOfTheManseEffect effect) {
        super(effect);
    }

    @Override
    public DanceOfTheManseEffect copy() {
        return new DanceOfTheManseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()));
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        if (source.getManaCostsToPay().getX() < 6) {
            return true;
        }
        cards.stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .forEach(permanent -> {
                    ContinuousEffect effect = new AddCardTypeTargetEffect(Duration.EndOfGame, CardType.CREATURE);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                    effect = new SetBasePowerToughnessTargetEffect(4, 4, Duration.EndOfGame);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                });
        return true;
    }
}